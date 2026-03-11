def call(String imageName, String kubeConfigId) {
    echo "Deploying to Kubernetes"
    
    // Using the "Secret file" added in Jenkins Credentials
    withCredentials([file(credentialsId: kubeConfigId, variable: 'KUBECONFIG')]) {
        
        // Replace the IMAGE_URL placeholder in the YAML file with the actual ECR image address
        sh "sed -i 's|IMAGE_URL|${imageName}|g' k8s-deploy.yaml"
        
        // Execute the deployment
        sh "kubectl --kubeconfig=\$KUBECONFIG apply -f k8s-deploy.yaml"
    }
    
    echo "Deployed successfully!"
}