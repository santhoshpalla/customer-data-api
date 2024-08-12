# customer-data-api
This API provides an interface for interacting with customer data

### prerequisites
````
Install following software to build and deploy this application
1. docker
2. docker-compose
3. docker local registry
4. kubernetes (eg: microk8s)
5. helm
````

# BUILD STEPS

### 1. In terminal, navigate to this project directory

### 2. Build docker image
```commandline
docker build -t customer-data-api-image .
```

### 3. Build docker-compose image
```commandline
docker-compose build
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
docker tag postgres localhost:5000/customer-data-db
```

### 6. Pushing to the local docker registry
```commandline
docker push localhost:5000/customer-data-api
```
```commandline
docker push localhost:5000/customer-data-db
```

# DEPLOY STEPS

### 1. Add local registry to helm chart
```commandline
helm registry localhost:5000
```

### 2. Install through helm
```commandline
helm install api-helm ./k8s/api-helm/
```

### 3. Check the resources of kubernetes
```commandline
kubectl get all
```

# TEST
Below provided curl commands can be used to test, replace the localhost with host ip address for using them

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
