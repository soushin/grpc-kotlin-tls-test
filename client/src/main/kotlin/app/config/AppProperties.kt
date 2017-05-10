package app.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 *
 * @author nsoushi
 */
@ConfigurationProperties("grpc")
class AppProperties {

    var server = Server()

    class Server {
        var hostname = "waterzooi.test.google.be"
        var port = 50051
        var tls = Tls()
        var usePlaintext: Boolean = false

        class Tls {
            var cert: String = "/cert/server.pem"
        }
    }
}