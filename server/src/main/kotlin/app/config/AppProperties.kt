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
        var tls = Tls()
        var usePlaintext: Boolean = false
        var openssl: Boolean = true

        class Tls {
            var cert: String = "/cert/server1.pem"
            var key: String = "/cert/server1.key"
        }
    }
}