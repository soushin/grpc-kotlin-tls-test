package app.grpc.server

import app.config.AppProperties
import grpc.server.gen.echo.EchoMessage
import grpc.server.gen.echo.EchoServiceGrpc
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.slf4j.LoggerFactory

/**
 *
 * @author nsoushi
 */
@GRpcService
class EchoServer(val appProperties: AppProperties) : EchoServiceGrpc.EchoServiceImplBase() {

    val log = LoggerFactory.getLogger(EchoServer::class.java)

    override fun echoService(request: EchoMessage?, responseObserver: StreamObserver<EchoMessage>?) {

        log.info(request?.message)

        val tsl = if (appProperties.server.openssl) {
            "TSL with OpenSSL"
        } else {
            "TSL with JDK"
        }

        val msg = EchoMessage.newBuilder().setMessage("echo \\${request?.message}/, $tsl").build()
        responseObserver?.onNext(msg)
        responseObserver?.onCompleted()
    }
}
