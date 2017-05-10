FROM java:openjdk-8

COPY . /grpc-client

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y ca-certificates openssl libc6 libapr1 && \
    echo "export LANG=C.UTF-8" > /etc/profile.d/locale.sh && \
    rm -rf /etc/localtime && \
    ln -sf /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    \
    cd /grpc-client && ./gradlew clean && \
    cd /grpc-client && rm -rf ~/.gradle && ./gradlew clean generateProto build -x test && \
    mkdir -p /usr/local/grpc-client/lib && \
    cp -R /grpc-client/build/libs/* /usr/local/grpc-client/lib/ && \
    rm -rf /grpc-client

ENTRYPOINT java $JAVA_OPTS -jar /usr/local/grpc-client/lib/grpc-client.jar

EXPOSE 8080 8080
EXPOSE 50051 50051
