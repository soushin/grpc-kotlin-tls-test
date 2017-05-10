package app.grpc.client

import app.config.AppProperties
import grpc.server.gen.echo.EchoMessage
import grpc.server.gen.echo.EchoServiceGrpc
import io.grpc.ManagedChannel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 *
 * @author nsoushi
 */
@Component
class EchoClient(val appProperties: AppProperties) : GRpcClient(), GRpcClientBase {

    val log = LoggerFactory.getLogger(EchoClient::class.java)

    fun request(req: String): String {
        var channel: ManagedChannel? = null

        try {
            channel = getChannel()
            val stub = EchoServiceGrpc.newBlockingStub(getChannel())
            return stub.echoService(EchoMessage.newBuilder().setMessage(req).build()).message
        } catch (e : Exception) {
            log.error(e.message, e.cause)
            return "error"
        } finally {
            channel?.shutdown()
        }
    }

    override fun getChannel(): ManagedChannel {
        return getChannelByProperties(appProperties)
    }
}
