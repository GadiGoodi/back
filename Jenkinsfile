pipeline {
    agent any

    environment {
        DOCKER_HUB_USERNAME = 'jaew0n'
        DOCKER_HUB_ACCESS_TOKEN = credentials('docker-hub-access-token')  // Jenkins credentials plugin 사용
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Check Branch') {
            steps {
                script {
                    def branchName = env.BRANCH_NAME
                    echo "Current branch: ${branchName}"
                    if (!branchName.contains("dev")) {
                        echo "Not on dev branch, skipping build."
                        currentBuild.result = 'ABORTED'
                        error("Stopping the build because it's not on the dev branch.")
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew build'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def imageName = "jaew0n/thiscoding"
                    def imageTag = "latest"

                    // Dockerfile 실행하여 도커 이미지 빌드
                    sh "docker build -t ${imageName}:${imageTag} ."
                    // 도커허브에 로그인
                    sh "echo ${DOCKER_HUB_ACCESS_TOKEN} | docker login -u ${DOCKER_HUB_USERNAME} --password-stdin"
                    // 도커 허브에 이미지 푸시
                    sh "docker push ${imageName}:${imageTag}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    def imageName = "jaew0n/thiscoding"
                    def imageTag = "latest"
                    def containerName = "thiscoding-container"

                    // Docker Hub에서 최신 이미지 풀
                    sh "docker pull ${imageName}:${imageTag}"

                    // 기존 컨테이너가 있다면 종료하고 제거
                    sh "docker rm -f ${containerName} || true"

                    // 새 컨테이너 실행
                    sh """
                        docker run -d \
                        --name ${containerName} \
                        --restart unless-stopped \
                        -p 80:8080 \
                        -v thiscoding-data:/app/data \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        ${imageName}:${imageTag}
                    """

                    // 배포 확인
                    sh "docker ps | grep ${containerName}"
                }
            }
        }
    }
}
