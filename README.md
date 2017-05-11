# grpc-kotlin-tls-test

## Overview

This repository contains the demonstration of gRPC built by Kotlin.  
This gRPC enables TLS on a server and client.

## Motivation

I want to know how to run grpc server with OpenSSL and with JDK.  
JDK must use `Jetty-ALPN jar`(if on Java < 8). 
OpenSSL does not run on `alpine image` so instead use `ubuntu image`.  
I organized `Dockerfile`. If you want to know, `server/Dockerfile.openssl` and `server/Dockerfile.jdk` will help you.  


## Running the examples

Add hosts
```
127.0.0.1 waterzooi.test.google.be
```

Note: use the test data certificate([grpc-go/testdata](https://github.com/grpc/grpc-go/tree/master/testdata)).

---

**Run both grpc servers**

```docker-compose shell
(grpc-kotlin-tls-test) $ docker-compose up -d
```

**Confirm grpc-server TSL with OpenSSL**

```running-client-server shell
(grpc-kotlin-tls-test/client) $ GRPC_SERVER_PORT=50052 GRPC_SERVER_TLS_CERT_FILE=$(pwd)/cert/ca.pem ./gradlew clean generateProto bootRun
```

then confirm to request over TLS with OpenSSL.
```
➜  ~ curl -XGET http://localhost:8080/echo
{"data":"echo \hello/, TSL with OpenSSL%"}%
```

`/echo` endpoint handles connect to gRPC server.

**Confirm grpc-server TSL with JDK**

```running-client-server shell
(grpc-kotlin-tls-test/client) $ GRPC_SERVER_PORT=50053 GRPC_SERVER_TLS_CERT_FILE=$(pwd)/cert/ca.pem ./gradlew clean generateProto bootRun
```

then confirm to request over TLS with JDK.
```
➜  ~ curl -XGET http://localhost:8080/echo
{"data":"echo \hello/, TSL with JDK"}%
```

`/echo` endpoint handles connect to gRPC server.
