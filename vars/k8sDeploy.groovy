def call(String imageName, String kubeConfigId, String awsAccountId, String region) {
    echo "Deploying to Kubernetes"
    
    withCredentials([file(credentialsId: kubeConfigId, variable: 'KUBECONFIG')]) {
        sh """
            export KUBECONFIG=${KUBECONFIG}
            TOKEN=\$(aws ecr get-login-password --region ${region})
            kubectl delete secret ecr-creds --ignore-not-found
            kubectl create secret docker-registry ecr-creds \
                --docker-server=${awsAccountId}.dkr.ecr.${region}.amazonaws.com \
                --docker-username=AWS \
                --docker-password=\$TOKEN
        """

        sh "sed -i 's|IMAGE_URL|${imageName}|g' k8s-deploy.yaml"
        
        sh "kubectl --kubeconfig=\$KUBECONFIG apply -f k8s-deploy.yaml --insecure-skip-tls-verify"
    }
}