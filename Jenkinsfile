pipeline {
  agent any
  stages {
    stage('test') {
      parallel {
        stage('test') {
          steps {
            echo 'reussite'
          }
        }

        stage('test1') {
          steps {
            sh 'echo \'bonjour\''
          }
        }

      }
    }

    stage('test3') {
      steps {
        echo 'bonsoir'
      }
    }

  }
}