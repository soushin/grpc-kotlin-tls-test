package app.grpc.server

import app.config.AppProperties
import io.grpc.ServerBuilder
import io.grpc.netty.GrpcSslContexts
import io.grpc.netty.NettyServerBuilder
import io.netty.handler.ssl.ClientAuth
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.SslProvider
import org.lognet.springboot.grpc.GRpcServerBuilderConfigurer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.File

/**
 *
 * @author nsoushi
 */
@Component
class MyGRpcServerBuilderConfigurer(val appProperties: AppProperties) : GRpcServerBuilderConfigurer() {

    val logger = LoggerFactory.getLogger(MyGRpcServerBuilderConfigurer::class.java)

    override fun configure(serverBuilder: ServerBuilder<*>?) {
        if (appProperties.server.usePlaintext) {
            logger.info("currency gRPC server built, the server skips negotiation.")
            serverBuilder?.build()
        } else {

            if (appProperties.server.openssl) {
                (serverBuilder as NettyServerBuilder).sslContext(
                        GrpcSslContexts.configure(SslContextBuilder
                                .forServer(File(appProperties.server.tls.cert),
                                        File(appProperties.server.tls.key)), SslProvider.OPENSSL)
                                .clientAuth(ClientAuth.OPTIONAL)
                                .build())
            } else {
                (serverBuilder as NettyServerBuilder).sslContext(
                        GrpcSslContexts.configure(SslContextBuilder
                                .forServer(File(appProperties.server.tls.cert),
                                        File(appProperties.server.tls.key)), SslProvider.JDK)
                                .clientAuth(ClientAuth.OPTIONAL)
                                .build())
            }
        }
    }
}