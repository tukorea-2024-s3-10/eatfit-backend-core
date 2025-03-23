pipeline {

    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    environment {
        AWS_REGION = 'ap-northeast-2'
    }

    stages {
        stage('Get Project From GitHub') {
            steps {
                git url: 'https://github.com/tukorea-2024-s3-10/eat-fit-backend-core-java-spring.git', branch: 'main'
            }
        }

        stage('Prepare application-sec.yaml') {
            steps {
                withCredentials([file(credentialsId: 'application-sec.yaml', variable: 'SEC_FILE')]) {
                    script {
                        sh "cp ${SEC_FILE} src/main/resources/application-sec.yaml"
                    }
                }
            }
        }

        stage('Build Project with Gradle') {
            steps {
                sh './gradlew clean bootJar'
            }
        }

        stage('Build Docker Image') {
            steps {
                withAWS(credentials: 'aws-credentials-id', region: 'ap-northeast-2') {
                    sh 'aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com'
                    sh 'docker build -t eatfit .'
                    sh 'docker tag eatfit:latest 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com/eatfit:latest'
                    sh 'docker push 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com/eatfit:latest'
                }
            }
        }
    }
}