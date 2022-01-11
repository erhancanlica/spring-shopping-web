pipeline {
    agent any
    tools {
        jdk 'jdk'
        maven '3.8.4'
       
    }
    stages {
        stage("build project") {
            steps {
                https://github.com/erhancnlc/shopping_web_be.git
                echo "Java VERSION"
                sh 'java -version'
                echo "Maven VERSION"
                sh 'mvn -version'
                echo 'building project...'
                sh "mvn compile"
                sh "mvn package"
                //sh "mvn test"
                sh "mvn clean install"
            }
        }
    }
}
