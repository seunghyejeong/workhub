

# edu-msa-master로 CICD 구축


### enviroment 
---
Ubuntu 22.04
CI: Jenkins 
CD: ArgoCD & GitOps
code analylist: SonarQube
---


### reference

[jenkins-docs](https://www.jenkins.io/doc/book/installing/kubernetes/) for more information.
[install-docker-in-k8s](https://devbksheen.tistory.com/entry/Jenkins%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Docker-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%9E%90%EB%8F%99-%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0Blue-Ocean-NCP) for more information.
[overall-flow-of-msa-deployment](https://jenakim47.tistory.com/74) for more information.
[image](./msa-project-workflow.png)

: 처음에 어떤 순서로 구축을 해야 하는지 몰라서 헤매었는데 이미지를 참고하여 '개발자'와 '운영자'의 기준으로 생각하니 훨씬 쉽게 다가왔음




## install jenkins
- jenkins는 k8s pod로 배포하였음.
- CI pipeline에 추가될 docker push로 jenkins pod안에 Docker가 설치 되어있어야 함.
- 일반적으로 jenkins 안에 docker를 구축하는 것은 권장하는 방법은 아님.


__Step 1__ download jenkins for kubernetes 
git clone https://github.com/scriptcamp/kubernetes-jenkins

__Step 2__ create manifestes.

- serviceAccount.yaml
```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  name: jenkins-admin
rules:
  - apiGroups: [""]
    resources: ["*"]
    verbs: ["*"]

---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: jenkins-admin
  namespace: devops-tools

---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: jenkins-admin
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: jenkins-admin
subjects:
- kind: ServiceAccount
  name: jenkins-admin
  namespace: devops-toolsubuntu
```
- volume.yaml
```yaml
--- #pv
apiVersion: v1
kind: PersistentVolume
metadata:
  name: jenkins-pv-volume
  labels:
    type: local
spec:
  storageClassName: local-storage
  claimRef:
    name: jenkins-pv-claim
    namespace: devops-tools
  capacity:
    storage: 10Gi
  accessModes:
    - ReadWriteOnce
  local:
    path: /mnt
  nodeAffinity:
    required:
      nodeSelectorTerms:
      - matchExpressions:
        - key: kubernetes.io/hostname
          operator: In
          values:
          - ta-bami-cluster-2       # 바꾸어줘야 하는 부분

--- #pvc
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-pv-claim
  namespace: devops-tools
spec:
  storageClassName: local-storage
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 3Gi
```

- deployment.yaml
```yaml
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
        runAsUser: 0
        fsGroup: 0      
      serviceAccountName: jenkins-admin
      containers:
        - name: jenkins
          image: seunghyejeong/docker-install:2.0
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
              mountPath: /var/run
      volumes:
        - name: jenkins-data
          persistentVolumeClaim:
            claimName: jenkins-pv-claim
        - name: jenkins-docker
          persistentVolumeClaim:
            claimName: jenkins-pv-claim-docker
```

- service.yaml
```yaml
apiVersion: v1
kind: Service
metadata:
  name: jenkins-service
  namespace: devops-tools
  annotations:
      prometheus.io/scrape: 'true'
      prometheus.io/path:   /
      prometheus.io/port:   '8080'
spec:
  selector: 
    app: jenkins-server
  type: NodePort  
  ports:
    - port: 8080
      targetPort: 8080
      nodePort: 32000
```
__STEP 3__  jenkins-ui 접속
http://{NODEIP}:{NODEPORT}로 접속


\*check\* 
- container image: seunghyejeong/docker-install:2.0
- docker 사용을 위해 root 권한 부여 
```yaml
    securityContext:
        runAsUser: 0
        fsGroup: 0 
```
- docker socket을 위한 volume mount

1) jenkins에 docker를 설치한 dockerimage를 registory에 올린다.

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
RUN apt-get update && apt-get install -y docker-ce docker-ce-cli containerd.io
RUN service docker start
RUN usermod -aG docker jenkins
USER jenkins
```
2) jenkins가 설치된 node에 접속하여 docker를 설치한다 (volume mount를 위해서)
`ssh {NODEIP}`
`docker run -d -it -v /var/run/docker.sock:/var/run/docker.sock -p 8090:8080 --name jenkins jenkins/jenkins:lts`


__STEP 4__  plugin 설치
github, mave, docker 등 CI를 위해 필요한 plugin을 설치한다.
설치 후 서비스 환경설정에 있는 default 값들을 입력 후 저장한다.


__STEP 5__  프로젝트 구성하기


### github edu-msa-master 구성도

gitops-repository
├──edu-msa-board-master
│       ├──Dockerfile
│       ├── Jenkinsfile
├── src
│   └── main
│       ├── java
│       ├── resources
│       │   └── properties
│       │       └── api.properties
│       ├── kubernetes
│       │       └── edu-msa-board.yaml
│       │       └── mysql-msa-board.yaml
│       ├── pom.xml
│       └── server.xml
├── edu-msa-comment-master
│       ├──Dockerfile
│       ├── Jenkinsfile
├── src
│   └── main
│       ├── java
│       ├── resources
│       │   └── properties
│       │       └── api.properties
│       ├── kubernetes
│       │       └── edu-msa-comment.yaml
│       │       └── mysql-msa-comment.yaml
│       ├── pom.xml
│       └── server.xml
├── edu-msa-ui-master 
│       ├──Dockerfile
│       ├── Jenkinsfile
├── src
│   └── main
│       ├── java
│       ├── resources
│       │   └── properties
│       │       └── api.properties
│       ├── kubernetes
│       │       └── edu-msa-ui.yaml
│       │       └── mysql-msa-ui.yaml
│       ├── pom.xml
│       └── server.xml
├── edu-msa-user-master
│       ├──Dockerfile
│       ├── Jenkinsfile
├── src
│   └── main
│       ├── java
│       ├── resources
│       │   └── properties
│       │       └── api.properties
│       ├── kubernetes
│       │       └── edu-msa-user.yaml
│       │       └── mysql-msa-user.yaml
│       ├── pom.xml
│       └── server.xml
├── edu-msa-zuul-master
│       ├── Jenkinsfile
│       ├── Dockerfile
├── src
│   └── main
│       ├── java
│       ├── resources
│       │   └── properties
│       │       └── api.properties
│       ├── kubernetes
│       │       └── edu-msa-zuul.yaml
│       ├── application.yaml
│       └── pom.xml
├── Jenkinsfile

- 하나의 디렉토리는 pod 하나를 뜻하고 한 디렉토리에 Jenkinsfile, Dockerfile, server.xml, pom.xml가 있어야 한다
- 웹에 배포할 경우 api.properties를 알맞게 수정한다.
- mysql-msa-*는 필요한 sql를 init.sql로 설계했다.

```yaml
apiVersion:  v1
kind: ConfigMap
metadata:
  name: mysql-msa-board-config
data:
  init.sql: |
    CREATE DATABASE msa_board default CHARACTER SET UTF8;
    USE msa_board;
		CREATE TABLE `TB_BOARD` (
		  `board_seq` int(11) NOT NULL AUTO_INCREMENT,
		  `board_title` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
		  `board_text` mediumtext COLLATE utf8_unicode_ci,
		  `write_user_id` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
		  `write_user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
		  `use_yn` varchar(1) COLLATE utf8_unicode_ci DEFAULT 'Y',
		  `create_dt` datetime NOT NULL,
		  `update_dt` datetime NOT NULL,
		  PRIMARY KEY (`board_seq`)
		) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql-msa-board
spec:
  selector:
    matchLabels:
      app: mysql-board
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: mysql-board
    spec:
      containers:
      - image: mysql:5.6
        name: mysql-board
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: password
        ports:
        - containerPort: 3306
          name: mysql-board
        volumeMounts:
        - name: mysql-msa-board-config
          mountPath: /docker-entrypoint-initdb.d/init.sql #configmap을 위한 volume연결
      volumes:
      - name: mysql-board
      - name: mysql-msa-board-config
        configMap:  #configmap을 위한 volume연결
          name: mysql-msa-board-config


---
apiVersion: v1
kind: Service
metadata:
  name: mysql-msa-board
spec:
  type: NodePort
  ports:
  - port: 3306
    protocol: TCP
    targetPort: 3306
    nodePort: 30501
  selector:
    app: mysql-board
```


__STEP 6__  새 아이템 생성으로 프로젝트 만들기 

- jenkins project build
1) + 새로운 아이템 -> pipeline project 생성
2) scm pipeline section -> select git
3) 
SCM: GIT
Repository: {GITURL}
Branchs to Build: /{BRANCH_NAME}
\*check\* 
script Path: {IF_REPOSITORY_EXSIST_REPLACE_IT}/Jenkinsfile
* scriptPath 첫글자 "/" 조심. (사용안됨)
4) apply

__STEP 6__ pipeline
- CI를 수행해야하는 해당 디렉토리에 Jenkinsfile을 만들어 pipeline을 삽입한다.

ex) edu-msa-board-mastesr

- Jenkinsfile
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
sh 'mvn -f /var/jenkins_home/workspace/msa-project/edu-msa-board-master/pom.xml clean package'  // pom.xml 파일을 직접 지정해준다.
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
    sh 'cd /var/jenkins_home/workspace/edu-msa-board/edu-msa-board-master && docker build -t edu-msa-board-master .'    
    sh 'docker tag edu-msa-board-master seunghyejeong/edu-msa-board-master:latest' //tag는 latest로해서 버전이 바뀌어도 yaml file은 변경되지 않도록 했다.
    sh 'docker push seunghyejeong/edu-msa-board-master:latest'
} 
}
}

}
}
```

- Dockerfile
```dockerfile
FROM tomcat:9-jre8-alpine
WORKDIR /usr/local/tomcat
COPY server.xml ./conf
RUN rm -rf ./webapps/*
ARG JAR_FILE=*.war
COPY ${JAR_FILE} ./webapps/edu-msa-board-1.0.0.war

EXPOSE 28082
```