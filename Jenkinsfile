pipeline {

    agent any

    stages {
        stage('Get Project From GitHub') {
            steps {
                git url: 'https://github.com/tukorea-2024-s3-10/eat-fit-backend-core-java-spring.git', branch: 'main'
            }
        }

        stage('Build Project with Gradle') {
            steps {
                sh './gradlew clean bootJar'
            }
        }
    }
}