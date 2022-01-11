pipeline {
    agent any
    stages {
        stage('git repo & clean') {
            steps {
                bat "mvn clean -f shopping_web_be"
            }
        }
        stage('install') {
            steps {
                bat "mvn install -f shopping_web_be"
            }
        }
        stage('test') {
            steps {
                bat "mvn test -f shopping_web_be"
            }
        }
        stage('package') {
            steps {
                bat "mvn package -f shopping_web_be"
            }
        }
    }
}
