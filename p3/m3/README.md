# This repository contains source code deliverables of my technical review for the Manning Distributed Tracing and Observability with Jaeger, Kubernetes, and Istio LiveProject.

### HOW TO RUN 
Build docker images of each microservices as indicated in their own README.md files. Then, deploy kubernetes objects following the instuctions below:

### DEPLOY KUBERNETES OBJECST
```
kubectl apply -f ./eshop.yaml
kubectl port-forward service/eshop 8080:8080
kubectl port-forward service/jaeger 16686:16686
```

### OPEN JAEGER UI & CALL CHECKOUT URL OF ESHOP 
* Jaeger UI url: http://localhost:16686
* Checkout with curl: curl 127.0.0.1:8080/checkout --header "user: ozay"
* response of curl command : eshop -> ozay's Order Created -> ozay's Order Payed -> ozay's Delivery Arranged -> ozay's Delivery transported
