# edu-msa-master로 CICD 구축 (3) sonarqube

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


## install sonarqube

__Step 1__ docker로 image pull
sudo docker pull sonarqube:lts-community

__Step 2__  docker run
sudo docker run -d --restart=always --name sonarqube -p 9000:9000 sonarqube:lts-community
http://[설치한 컴퓨터의 IP 주소]:9000 에 접속
_vm 환경인 경우 설치된 node( 내경우에는 master) IP
__Step 3__  token 발급
jenkins와 연동을 위해 token을 발급 받는다.

## set jenkins env of sonarqube

__Step 1__  sonarqube 환경설정	
- jenkins-> global configuration -> sonarqube scanner 등록
- jenkins-> system 설정 -> sonarqube server 등록
- sonarqube plugin이 설치되어 있지 않다면 plugin 설치부터 해주어야함
\*check\*
이때 sonarqube scanner와 sonarqube server의 이름이 같아야한다.

__Step 2__  create a Jenkinsfile for sonarqube in git repository
- 각 레파지토리에는 CI를 위한 Jenkinsfile이 존재하고 있어 top repository에 sonarqube를 위한 Jenkinsfile 생성
- 배열로 디렉토리를 선언한 뒤 project를 하나씩 돌아가며 소스 코드를 검사한다.

```groovy 
def projects = [
  'edu-msa-board-master',
  'edu-msa-comment-master',
  'edu-msa-ui-master',
  'edu-msa-user-master'
]

pipeline {
  agent any
  tools {
    maven 'maven' 
  }

  stages {
    stage('Checkout Projects') {
      steps {
        git url: 'https://github.com/seunghyejeong/gitops-repository.git', branch: 'main'
      }
    }

    stage('SonarQube Analysis') {
      steps {
        script {
       	 withCredentials([
          string(credentialsId: 'jenkins-sonarqube', variable: 'SONAR_TOKEN')
       	 ]) {
          for (def project in projects) {
            dir(project) {
              withSonarQubeEnv('sonarqube') {
                sh 'mvn clean package sonar:sonar'
  }
              }
            }
          }
        }
      }
    }
  }
}
```