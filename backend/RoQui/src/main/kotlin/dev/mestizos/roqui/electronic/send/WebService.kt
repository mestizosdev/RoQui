package dev.mestizos.roqui.electronic.send

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WebService {

    @Value("\${sri.url.ws.developer.reception}")
    lateinit var developerReception: String

    @Value("\${sri.url.ws.developer.authorization}")
    lateinit var developerAuthorization: String

    @Value("\${sri.url.ws.production.reception}")
    lateinit var productionReception: String

    @Value("\${sri.url.ws.production.authorization}")
    lateinit var productionAuthorization: String

    fun printPropertyValues() {
        println("Developer reception: $developerReception")
        println("Developer authorization: $developerAuthorization")
        println("Production reception: $productionReception")
        println("Production authorization: $productionAuthorization")
    }
}