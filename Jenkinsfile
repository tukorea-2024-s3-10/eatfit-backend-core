pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user'
        EC2_HOST = '10.0.1.193'  // 실제 EC2 퍼블릭 IP나 도메인으로 변경
        DEPLOY_PATH = '/home/ec2-user'
        SSH_KEY_CREDENTIALS_ID = 'eatfit-ssh-key-id' // Jenkins에 등록된 SSH 개인키 크리덴셜 ID
    }

    stages {
        stage('Get Project From GitHub') {
            steps {
                git url: 'https://github.com/tukorea-2024-s3-10/eat-fit-backend-core-java-spring.git', branch: 'main'
            }
        }

        stage('프로젝트 Security 파일 주입') {
            steps {
                withCredentials([file(credentialsId: 'application-prod.yaml', variable: 'SEC_FILE')]) {
                    script {
                        sh "cp ${SEC_FILE} src/main/resources/application-prod.yaml"
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

        stage('Copy JAR to Backend Server') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: SSH_KEY_CREDENTIALS_ID, keyFileVariable: 'SSH_KEY')]) {
                    sh """
                        scp -i ${SSH_KEY} -o StrictHostKeyChecking=no build/libs/eat-fit-0.0.1-SNAPSHOT.jar ${EC2_USER}@${EC2_HOST}:/home/ec2-user/eatfit-backend.jar
                    """
                }
            }
        }

        stage('Run Deploy Script on Backend Server') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: SSH_KEY_CREDENTIALS_ID, keyFileVariable: 'SSH_KEY')]) {
                    sh """
                        ssh -i ${SSH_KEY} -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_HOST} 'bash /home/ec2-user/deploy.sh'
                    """
                }
            }
        }
    }
}
