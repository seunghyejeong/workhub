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

// stage('ready to docker build'){
// steps {
// //dir('msa-project/edu-msa-ui-master'){
// //sh 'cp ./target/*.war ../edu-msa-file-master/Docker/edu-msa-ui-msater/'
// //sh 'cp /var/jenkins_home/workspace/msa-project/edu-msa-ui-master/target/*.war /var/jenkins_home/workspace/msa-project/edu-msa-file-master/Docker/edu-msa-ui
// '
// //}
// }
// }

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
// dir(' /var/jenkins_home/workspace/msa-project/edu-msa-ui-master/Docker/'){
// sh 'docker build -t edu-msa-ui-master .' 
// sh 'docker tag edu-msa-ui-master seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER' 
// sh 'docker push seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER' 
    dir(' /var/jenkins_home/workspace/msa-project/edu-msa-ui-master/'){
    sh 'docker build -t edu-msa-ui-master .'
    sh 'docker tag edu-msa-ui-master seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER'
    sh 'docker push seunghyejeong/edu-msa-ui-master:$BUILD_NUMBER'
    }
} 
}
}

}
}



