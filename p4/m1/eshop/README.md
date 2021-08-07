## HOW TO RUN

### PACKAGE
`mvn clean package spring-boot:repackage`
### BUILD DOCKER IMAGE
`docker build -t ozay/eshop .`
`docker image tag ozay/eshop ozay/eshop:v4.1`
`docker push ozay/eshop:v4.1`

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


### Resource 
https://developer.ibm.com/tutorials/distributed-tracing-for-microservices-1/
### environment variables:
JAEGER_ENDPOINT=http://127.0.0.1:14268/api/traces;JAEGER_SAMPLER_TYPE=const;JAEGER_SAMPLER_PARAM=1;JAEGER_REPORTER_LOG_SPANS=true;JAEGER_PROPAGATION=b3
ENV JAEGER_ENDPOINT="http://127.0.0.1:14268/api/traces"
ENV JAEGER_SAMPLER_TYPE=const
ENV JAEGER_SAMPLER_PARAM=1
ENV JAEGER_REPORTER_LOG_SPANS=true
ENV JAEGER_PROPAGATION=b3


```
docker run -d --name jaeger -p 14268:14268 -p 16686:16686 jaegertracing/all-in-one:1.24
```
### All in One
docker run -d --name jaeger \
-e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
-p 5775:5775/udp \
-p 6831:6831/udp \
-p 6832:6832/udp \
-p 5778:5778 \
-p 16686:16686 \
-p 14268:14268 \
-p 14250:14250 \
-p 9411:9411 \
jaegertracing/all-in-one:1.24

### Jaeger UI url: http://localhost:16686
