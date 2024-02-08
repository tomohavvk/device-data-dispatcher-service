## start flow

- minikube start
- minikube tunnel
- kubectl create deployment device-data-handler-service --image=ihorzadyra/device-data-handler-service:0.0.3-SNAPSHOT --port=9000

- kubectl expose deployment device-data-handler-service --type=LoadBalancer --port=9000
- OR
- kubectl expose deployment device-data-handler-service --type=NodePort --port=9000
- kubectl get svc
  - to check the external ip

## alternative way
kubectl apply -f ./charts/device-data-dispatcher-service.yaml