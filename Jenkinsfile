pipeline {

    agent any

    environment {
        // Global variables
        KUBERNETES_CREDENTIALS = "k8s-kubeconfig"
        NEXUS_CREDENTIALS      = "mydnacodes-nexus-user"
        DOCKER_CREDENTIALS     = "mydnacodes-docker-user"
        // Local variables
        DOCKER_IMAGE_TAG       = "mydnacodes/notification-service"
        DOCKER_IMAGE           = ""
        COMMIT_AUTHOR          = ""
        COMMIT_MESSAGE         = ""
        PROJECT_VERSION        = ""
    }

    tools {
        maven "mvn-3.6"
        jdk "jdk-11"
    }

    stages {
        stage("Set environment variables") {
            steps {
                script {
                    pom                  = readMavenPom file:"pom.xml"
                    PROJECT_VERSION      = pom.version
                    COMMIT_MESSAGE       = sh script: "git show -s --pretty='%s'", returnStdout: true
                    COMMIT_AUTHOR        = sh script: "git show -s --pretty='%cn <%ce>'", returnStdout: true
                    COMMIT_AUTHOR        = COMMIT_AUTHOR.trim()
                }
            }
        }
        stage("Package application") {
            steps {
                sh "mvn clean package -DskipTests=true"
            }
        }
        stage("Build docker image") {
            steps {
                script {
                    dockerImage = docker.build DOCKER_IMAGE_TAG
                }
            }
        }
        stage("Publish docker image") {
            steps {
                script {
                    docker.withRegistry("", DOCKER_CREDENTIALS) {
                        dockerImage.push("$PROJECT_VERSION")
                        dockerImage.push("latest")
                    }
                }
            }
        }
        stage("Clean docker images") {
            steps {
                sh "docker rmi $DOCKER_IMAGE_TAG:$PROJECT_VERSION"
                sh "docker rmi $DOCKER_IMAGE_TAG:latest"
            }
        }
        stage("Deploy libraries") {
           steps {
               withCredentials([usernamePassword(credentialsId: NEXUS_CREDENTIALS, passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                   sh "mvn clean deploy -DskipTests=true -Dnexus.username=$USERNAME -Dnexus.password=$PASSWORD --settings .ci/settings.xml -P lib"
               }
           }
        }
        stage("Clean maven packages") {
            steps {
                sh "mvn clean"
            }
        }
        stage("Prepare deployments") {
            steps {
                script {
                    def deploymentConfig = readYaml file: ".ci/deployment-config.yaml"
                    def environment      = ""

                    if (env.GIT_BRANCH.equals("prod") || env.GIT_BRANCH.equals("origin/prod")) {
                        environment = deploymentConfig.environments.prod
                    } else {
                        environment = deploymentConfig.environments.dev
                    }

                    sh """ \
                    sed -e 's+{{IMAGE_NAME}}+$DOCKER_IMAGE_TAG:$PROJECT_VERSION+g' \
                        -e 's+{{NAMESPACE}}+$environment.namespace+g' \
                        -e 's+{{ENV_SUFFIX}}+$environment.suffix+g' \
                        -e 's+{{VERSION}}+$PROJECT_VERSION+g' \
                        -e 's+{{ENV_NAME}}+$environment.name+g' \
                        -e 's+{{ENV_PROD}}+$environment.prod+g' \
                        .kube/notification-service.yaml > .kube/notification-service.tmp
                    """
                    sh "mv -f .kube/notification-service.tmp .kube/notification-service.yaml"

                    sh """ \
                    sed -e 's+{{NAMESPACE}}+$environment.namespace+g' \
                        .kube/notification-service-db.yaml > .kube/notification-service-db.tmp
                    """
                    sh "mv -f .kube/notification-service-db.tmp .kube/notification-service-db.yaml"
                }
            }
        }
        stage("Deploy application") {
            steps {
                script {
                    try {
                        if (!(env.GIT_BRANCH.equals("prod") || env.GIT_BRANCH.equals("origin/prod"))) {
                            withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS]) {
                                sh "kubectl delete deployments.apps -n mydnacodes notification-service-app"
                            }
                        }
                    } catch (Exception e) {
                        echo "Deployment has not been scaled."
                        echo e.getMessage()
                    }
                }

                withKubeConfig([credentialsId: KUBERNETES_CREDENTIALS]) {
                    sh "kubectl apply -f .kube/"
                }
            }
        }
    }
    post {
        success {
            slackSend (color: '#57BA57',
                       message: """[<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *SUCCESSFUL*\n
                                  |Job: *${env.JOB_NAME}*\n
                                  |Branch: ${GIT_BRANCH}
                                  |Author: ${COMMIT_AUTHOR}
                                  |Message: ${COMMIT_MESSAGE}""".stripMargin()
            )
        }
        failure {
            slackSend (color: '#BD0808',
                       message: """[<${env.BUILD_URL}|Build ${env.BUILD_NUMBER}>] *FAILED*\n
                                  |Job: *${env.JOB_NAME}*\n
                                  |Branch: ${GIT_BRANCH}
                                  |Author: ${COMMIT_AUTHOR}
                                  |Message: ${COMMIT_MESSAGE}""".stripMargin()
            )
        }
    }
}