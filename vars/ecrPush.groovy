def call(String repoName, String awsAccountId, String region = "eu-central-1") {
    echo "Sending image to AWS ECR."
    
    String ecrUrl = "${awsAccountId}.dkr.ecr.${region}.amazonaws.com"
    
    // Login to ECR
    sh "aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrUrl}"
    
    // Tag and push
    sh "docker tag ${repoName}:latest ${ecrUrl}/${repoName}:latest"
    sh "docker push ${ecrUrl}/${repoName}:latest"
    
    echo "Image sent to ECR!"
}