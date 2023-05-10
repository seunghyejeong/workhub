# ~ 230510 


1) Jenkins pod에 Docker를 설치하는 것은 추천하는 방법은 아니다.
2) 결국 pod안에 Docker를 설치하기 위해서는 node와 volume 연결 하는 방법 뿐인데 이는 pod가 배포된 해당 node에 docker가 설치 되어있어야 한다.
3) Dockerfile
    - Dockerfile의 실행 명령어는 Dockerfile이 존재하는 해당 폴더 기준이다.
    - 실행되는 명령어는 DockerImage안에 형성된다.
4) Java - maven - tomcat 
    - projecto내에 CI를 위한 Jenkinsfile, build를 위한 Dockerfile, .war 및 프로젝트를 구성하는 기본 디렉토리를 포함한다.
    - pod안에 Docker가 설치되어 있다는 가정하에 진행된다.
    - Jenkinsfile에서 mvn tools로 maven을 실행 시킬 수 있으며 Dockerfile에서는 mvn이 포함된 tomcat-jdk image를 다운로드 받는다.
    - war파일이 생성되는 즉 image를 만다는 기준은 maven build를 뜻한다. eclipse나 IDE tool 이용시 run as > maven build이며 war파일을 포함 server.xml 기준으로 project가 배포 및  tomcat 실행이 진행된다.


# docker in jenkins 
https://devbksheen.tistory.com/entry/Jenkins%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Docker-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%9E%90%EB%8F%99-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0Blue-Ocean-NCP

- jenkins 안에 docker를 구축하는 것은 권장하는 방법은 아님
- jenkins가 설치되어있는 서버의 docker 볼륨과 jenkins 컨테이너 안의 docker 볼륨을 연결해 사용함
`docker run -d -it -v /var/run/docker.sock:/var/run/docker.sock -p 8090:8080 --name jenkins jenkins/jenkins:lts`
- 안되면 직접 서버를 설치한다
  1) ssh {DOCKER_INSTALL_NODE_IP}
  2) 해당 node에 docker install
  3) master 계정 접속
  4) docker exec -it -u root jenkins bash : root권한으로 접속
  5) chown jenkins:jenkins /var/run/docker.sock

```console
CONTAINER ID   IMAGE                 COMMAND                  CREATED          STATUS          PORTS                                                  NAMES
86aa0d5ea8c2   jenkins/jenkins:lts   "/usr/bin/tini -- /u…"   16 minutes ago   Up 16 minutes   50000/tcp, 0.0.0.0:8090->8080/tcp, :::8090->8080/tcp   jenkins
```
---

위의 방법은 docker로 container 설치하는 방법.

1. jenkins가 설치된 node에 docker를 설치해준다.

```Dockerfile
FROM jenkins/jenkins:2.387.2
USER root
RUN apt-get update && apt-get install -y lsb-release
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg
RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli
USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"

docker build -t myjenkins-blueocean:2.387.2-1 .

docker run \
  --name jenkins-blueocean \
  --restart=on-failure \
  --detach \
  --network jenkins \
  --env DOCKER_HOST=tcp://docker:2376 \
  --env DOCKER_CERT_PATH=/certs/client \
  --env DOCKER_TLS_VERIFY=1 \
  --publish 8080:8080 \
  --publish 50000:50000 \
  --volume jenkins-data:/var/jenkins_home \
  --volume jenkins-docker-certs:/certs/client:ro \
  myjenkins-blueocean:2.387.2-1 
```

2. local(jenkins server)의 /var/run(docker.sock)을 volume mount 해준다

```yaml 
#volume-docker.yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: jenkins-pv-volume-docker
  labels:
    type: local
spec:
  storageClassName: local-storage
  claimRef:
    name: jenkins-pv-claim-docker
    namespace: devops-tools
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  local:
          path: /var/run
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - ta-bami-cluster-2
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-pv-claim-docker
  namespace: devops-tools
spec:
  storageClassName: local-storage
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 3Gi
```
3. deployment를 수정한다 
  - docker는 root로만 실행할 수 있다.
  - volume mount를 선언한다.
  - docker가 설치된 jenkins image로 pull한다.

```yaml 
#deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jenkins
  namespace: devops-tools
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins-server
  template:
    metadata:
      labels:
        app: jenkins-server
    spec:
      securityContext: 
        runAsUser: 0  # root 권한으로 pod 실행
        fsGroup: 0     # root 권한으로 pod 실행 
      serviceAccountName: jenkins-admin
      containers:
        - name: jenkins
          image: seunghyejeong/docker-install:2.0 # docker가 설치된 image registry
          resources:
            limits:
              memory: "2Gi"
              cpu: "1000m"
            requests:
              memory: "500Mi"
              cpu: "500m"
          ports:
            - name: httpport
              containerPort: 8080
            - name: jnlpport
              containerPort: 50000
          livenessProbe:
            httpGet:
              path: "/login"
              port: 8080
            initialDelaySeconds: 90
            periodSeconds: 10
            timeoutSeconds: 5
            failureThreshold: 5
          readinessProbe:
            httpGet:
              path: "/login"
              port: 8080
            initialDelaySeconds: 60
            periodSeconds: 10
            timeoutSeconds: 5
            failureThreshold: 3
          volumeMounts:
            - name: jenkins-data
              mountPath: /var/jenkins_home
            - name: jenkins-docker
              mountPath: /var/run # docker.sock 있는 directory
      volumes:
        - name: jenkins-data
          persistentVolumeClaim:
            claimName: jenkins-pv-claim
        - name: jenkins-docker
          persistentVolumeClaim:
            claimName: jenkins-pv-claim-docker 
```
4. docker ps로 확인해본다
`k exec -it -n devops-tools jenkins-858cddb78b-mz6zb -- bash`

`docker ps`
: 이전의 출력
```console
root@jenkins-6746745649-hljzr:~# docker ps
Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?
```
: 설치 후 출력
```console
root@jenkins-858cddb78b-mz6zb:~# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
```



# linux jenkins 

- jenkins ui project build
1) + 새로운 아이템 -> pipeline project 생성
2) scm pipeline section -> select git
3) 
SCM: GIT
Repository: GIT URL
Branchs to Build: /main
script Path: {IF_FOLDER_EXSIST_FOLDERNAME}/Jenkinsfile
* scriptPath에 "/" 조심. 
4) apply





# repository folder list
```command
├── Dockerfile
├── Jenkinsfile
├── manifest.yml
├── pom.xml
├── server.xml
├── src
│   └── main
│       ├── java
│       │   └── paasta
│       │       └── msa
│       ├── resources
│       │   ├── log4j2.xml
│       │   └── properties
│       │       └── api.properties
│       └── webapp
│           ├── META-INF
│           │   └── MANIFEST.MF
│           ├── WEB-INF
│           │   ├── config
│           │   ├── jsp
│           │   └── web.xml
│           ├── common
│           ├── css
│           ├── font
│           ├── images
│           ├── index.jsp
│           └── js
│               └── jquery-3.6.0.min.js
└── target
    ├── classes
    │   ├── egovframework
    │   ├── log4j2.xml
    │   ├── paasta
    │   └── properties
    │       └── api.properties
    ├── edu-msa-ui-1.0.0
    │   ├── META-INF
    │   │   └── MANIFEST.MF
    │   ├── WEB-INF
    │   │   ├── classes
    │   │   │   ├── egovframework
    │   │   │   └── properties
    │   │   │       └── api.properties
    │   │   ├── config
    │   │   ├── jsp
    │   │   ├── lib
    │   │   └── web.xml
    │   ├── common
    │   │   └── error.jsp
    │   ├── css
    │   ├── font
    │   ├── images
    │   ├── index.jsp
    │   └── js
    ├── edu-msa-ui-1.0.0.war
    ├── m2e-wtp
    │   └── web-resources
    │       └── META-INF
    │           ├── MANIFEST.MF
    │           └── maven
    │               └── paasta.msa
    │                   └── edu-msa-ui
    │                       ├── pom.properties
    │                       └── pom.xml
    ├── maven-archiver
    │   └── pom.properties
    └── maven-status
        └── maven-compiler-plugin
            └── compile
                └── default-compile
                    ├── createdFiles.lst
                    └── inputFiles.lst
```

# pipeline 
```groovy
pipeline {
  agent any

  tools{
  maven 'maven' 
  }

stages {
stage('Checkout Application Git Branch') {
steps {
git url: 'https://github.com/seunghyejeong/gitops-repository.git', branch: 'main'
}        
post {
failure {
echo 'Failed to clone repository'
}
success {
echo 'Repository cloned successfully'
}
}
}

stage('Maven Jar Build') {
steps {
sh 'mvn -f /var/jenkins_home/workspace/msa-project/edu-msa-ui-master/pom.xml clean package'
}
post {
failure {
echo 'Failed to build Maven'
}
success {
echo 'Maven war built successfully!'
}
}
}

stage('Docker Login'){
steps{
withCredentials([usernamePassword(credentialsId: 'docker-token', passwordVariable: 'tlqkfak0315!', usernameVariable: 'seunghyejeong')]) {
sh 'docker login -u seunghyejeong -p tlqkfak0315!'
}   
}
}

stage('Deploy our image') { 
steps { 
script {
    sh 'cd /var/jenkins_home/workspace/msa-project/edu-msa-ui-master && docker build -t edu-msa-ui-master .'
    sh 'docker tag edu-msa-ui-master seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER'
    sh 'docker push seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER'
} 
}
}

}
}
```

# dockerfile
```Dockerfile
# Start with a base image containing Tomcat server running with JRE8
FROM maven:3.6.0-jdk-8-slim AS build

# Set the working directory to /app
WORKDIR /app

# Copy the pom.xml and src directories to the container
COPY pom.xml .
COPY src ./src

# Build the application with Maven
RUN mvn package

# Start with a base image containing Tomcat server running with JRE8
FROM tomcat:9-jre8-alpine

# Set the working directory to /usr/local/tomcat
WORKDIR /usr/local/tomcat

# Copy the custom server.xml file to the container
COPY server.xml ./conf

# Remove any existing files in the webapps directory of Tomcat
RUN rm -rf ./webapps/*

# Copy the WAR file generated by Maven into the webapps directory of Tomcat
ARG JAR_FILE=*.war
COPY --from=build /app/target/${JAR_FILE} ./webapps/edu-msa-ui-1.0.0.war

# Expose the port the application will listen on
EXPOSE 8080

# Start the Tomcat server
CMD ["catalina.sh", "run"]
```
