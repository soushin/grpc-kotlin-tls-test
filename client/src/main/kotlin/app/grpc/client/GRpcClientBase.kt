package app.grpc.client

import io.grpc.ManagedChannel

/**
 *
 * @author nsoushi
 */
interface GRpcClientBase {

    fun getChannel(): ManagedChannel
}