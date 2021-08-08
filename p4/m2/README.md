# This repository contains source code deliverables of my technical review for the Manning Distributed Tracing and Observability with Jaeger, Kubernetes, and Istio LiveProject.

### INSTALL ISTIO & CONFIGURE FOR JAEGER
"C:\Program Files\Kubernetes\istio-1.10.3\bin\istioctl" install -y -f ./istio.yaml
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.10/samples/addons/jaeger.yaml
kubectl label namespace default istio-injection=enabled --overwrite=true
([for more about sidecar-injection](https://istio.io/latest/docs/setup/additional-setup/sidecar-injection/))

### HOW TO RUN 
Build docker images of each microservices as indicated in their own README.md files. Then, deploy kubernetes objects following the instuctions below:

### DEPLOY KUBERNETES & ISTIO OBJECTS
```
kubectl apply -f ./eshop.yaml
kubectl apply -f ./vs-gateway.yaml
kubectl port-forward service/istio-ingressgateway 8080:80 -n istio-system 
```

### OPEN JAEGER UI & CALL CHECKOUT URL OF ESHOP 
* Jaeger UI url: http://127.0.0.1:8080
* Checkout with curl: curl 127.0.0.1:8080/checkout
* response of curl command : eshop -> Order Created -> Order Payed -> Delivery Arranged -> Delivery transported
