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

        stage('Prepare application-sec.yaml') {
            steps {
                withCredentials([file(credentialsId: 'application-sec.yaml', variable: 'application-sec.yaml')]) {
                    script {
                        sh "cp ${application-sec.yaml} src/main/resources/application-sec.yaml"
                    }
                }
            }
        }

        stage('Build Project with Gradle') {
            steps {
                sh './gradlew clean bootJar'
            }
        }
    }
}