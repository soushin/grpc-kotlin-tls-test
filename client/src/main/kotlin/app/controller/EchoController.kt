package app.controller

import app.grpc.client.EchoClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity.ok
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author nsoushi
 */
@RestController
class EchoController(val echoClient: EchoClient) {

    @GetMapping(value = "/echo")
    fun index() = try {
        ok(echoClient.request("hello"))
    } catch (e :Exception) {
        status(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}