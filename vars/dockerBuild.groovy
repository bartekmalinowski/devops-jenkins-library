def call(String imageName) {
    echo "Starting build of image: ${imageName}"
    
    sh "docker build -t ${imageName}:${env.BUILD_NUMBER} ."
    sh "docker tag ${imageName}:${env.BUILD_NUMBER} ${imageName}:latest"
    
    echo "Image ${imageName} built and tagged!"
}