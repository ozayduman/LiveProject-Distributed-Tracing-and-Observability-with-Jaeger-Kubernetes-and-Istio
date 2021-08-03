# This repository contains source code deliverables of my technical review for the Manning [Distributed Tracing and Observability with Jaeger, Kubernetes, and Istio](https://liveproject.manning.com/project/621) LiveProject.

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
* Checkout url: http://localhost:8080/checkout