pipeline {

    agent any

    triggers {
        pollSCM('*/3 * * * *')
    }

    stages {
        stage('Get Project From GitHub') {
            steps {
                git url: 'https://github.com/tukorea-2024-s3-10/eat-fit-backend-core-java-spring.git', branch: 'main'
            }
        }

        stage('프로젝트 Security 파일 주입') {
            steps {
                withCredentials([file(credentialsId: 'application-sec.yaml', variable: 'SEC_FILE')]) {
                    script {
                        sh "cp ${SEC_FILE} src/main/resources/application-sec.yaml"
                    }
                }
            }
        }

        stage('Spring Boot 프로젝트 빌드') {
            steps {
                sh './gradlew clean bootJar'
            }
            post {
                failure {
                    error "Spring Boot 프로젝트 빌드 실패"
                }
                success {
                    echo "Spring Boot 프로젝트 빌드 성공"
                }
            }
        }

        stage('Docker 빌드 후 ECR 업로드') {
            steps {
                withAWS(credentials: 'aws-credentials-id', region: 'ap-northeast-2') {
                    sh 'aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com'
                    sh 'docker build -t eatfit/backend-core .'
                    sh 'docker tag eatfit/backend-core:latest 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com/eatfit/backend-core:latest'
                    sh 'docker push 248189949085.dkr.ecr.ap-northeast-2.amazonaws.com/eatfit/backend-core:latest'
                }
            }
        }

        stage('Update ECS Service') {
            steps {
                withAWS(credentials: 'aws-credentials-id', region: 'ap-northeast-2') {
                    script {
                        sh 'aws ecs update-service --cluster eatfit-cluster --service eatfit-backend-core-service --force-new-deployment'
                    }
                }
            }
        }
    }
}