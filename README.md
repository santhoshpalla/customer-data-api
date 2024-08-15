# customer-data-api
This API provides an interface for interacting with customer data

### prerequisites
````
Install following software to build and deploy this application
1. docker
2. docker-compose
3. docker local registry
4. kubernetes (eg: microk8s)
6. ingress should be enabled 
7. helm
````

# BUILD STEPS

### 1. In terminal, navigate to this project directory

### 2. Build docker image
```commandline
docker build -t customer-data-api-image .
```

### 3. To verify the image
```commandline
docker images
```

### 4. Setup a local docker registry
```commandline
docker run -d -p 5000:5000 --restart always --name registry registry
```

### 5. tag docker api image and pull postgresql image
```commandline
docker tag customer-data-api-image localhost:5000/customer-data-api
```
```commandline
docker pull postgres:alpine
```
```commandline
docker tag postgres:alpine localhost:5000/customer-data-db
```

### 6. Pushing to the local docker registry
```commandline
docker push localhost:5000/customer-data-api
```
```commandline
docker push localhost:5000/customer-data-db
```

# DEPLOY STEPS

### 1. Install through helm
```commandline
helm install apichart ./k8s/apichart/
```

### 2. Check the resources of kubernetes
```commandline
kubectl get all
```

### 3. Port forward from localhost port to NodePort
```commandline
kubectl port-forward svc/apichart 8081:8081
```

# OPEN TELEMETRY
```commandline
helm install my-otel-demo open-telemetry/opentelemetry-demo
```

```
kubectl port-forward svc/my-otel-demo-frontendproxy 8080:8080
```

# TEST
Below provided curl commands can be used to test

### 1. get all customers
```commandline
curl --location 'localhost:8081/api/v1/customers'
```

### 2. get customer by id
```commandline
curl --location 'localhost:8081/api/v1/customers/2'
```

### 3. get customer by email id
```commandline
curl --location 'localhost:8081/api/v1/customers/customer/details?emailId=abc@outlook.com'
```

### 4. create new customer
```commandline
curl --location 'localhost:8081/api/v1/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "prefix": "Mr.",
    "suffix": null,
    "firstName": "John",
    "middleName": null,
    "lastName": "Doe",
    "emailId": "johndoe@outlook.com",
    "phoneNumber": "+1(523)305-3324"
}'
```

### 5. update customer
```commandline
curl --location --request PUT 'localhost:8081/api/v1/customers/3' \
--header 'Content-Type: application/json' \
--data-raw '{
    "prefix": "Mr.",
    "suffix": null,
    "firstName": "john",
    "middleName": null,
    "lastName": "doe",
    "emailId": "johndoe@outlook.com",
    "phoneNumber": "+1(326)233-3434"
}'
```

### 6. Delete customer
```commandline
curl --location --request DELETE 'localhost:8081/api/v1/customers/3'
```
