## start flow
- minikube start
- kubectl apply -f ./charts/zookeeper-deployment.yaml
- kubectl apply -f ./charts/kafka-deployment.yaml
- kubectl apply -f ./charts/schema-registry-deployment.yaml
- kubectl apply -f ./charts/device-data-dispatcher-service-deployment.yaml

## alternative way
- kubectl expose deployment device-data-dispatcher-service --type=LoadBalancer --port=9000
- OR
- kubectl expose deployment device-data-dispatcher-service --type=NodePort --port=9000


## For testing
- minikube dashboard
- kubectl port-forward deployments/kafka  9093:9093
- kubectl port-forward deployments/schema-registry 8081:8081
- kubectl port-forward deployments/device-data-dispatcher-service 9000:9000

```json
curl -X 'POST' \
  'http://localhost:9000/api/v1/device/data/location' \
  -H 'accept: application/json' \
  -H 'X-Trace-Id: 953a5959-1b0f-412a-bdab-1cbe15486a28' \
  -H 'Content-Type: application/json' \
  -d '{
  "device_id": "007b4ad7-4b34-4b17-be73-7b3b233b531f",
  "latitude": 48,
  "longitude": 38,
  "time": 1707402580
}'
```