package app.grpc.client

import app.config.AppProperties
import io.grpc.ManagedChannel
import io.grpc.netty.GrpcSslContexts
import io.grpc.netty.NettyChannelBuilder
import org.slf4j.LoggerFactory
import java.io.File

/**
 *
 * @author nsoushi
 */
abstract class GRpcClient {

    val logger = LoggerFactory.getLogger(GRpcClient::class.java)

    protected fun getChannelByProperties(appProperties: AppProperties): ManagedChannel {

        if (appProperties.server.usePlaintext) {
            logger.info("currency gRPC client built, the client skips negotiation.")
            return NettyChannelBuilder
                    .forAddress(appProperties.server.hostname,
                            appProperties.server.port)
                    .usePlaintext(true)
                    .build()
        } else {
            return NettyChannelBuilder
                    .forAddress(appProperties.server.hostname,
                            appProperties.server.port)
                    .sslContext(
                            GrpcSslContexts.forClient()
                                    .trustManager(File(appProperties.server.tls.cert)).build())
                    .build()
        }
    }
}