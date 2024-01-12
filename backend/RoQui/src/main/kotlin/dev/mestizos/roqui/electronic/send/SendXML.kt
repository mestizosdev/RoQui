package dev.mestizos.roqui.electronic.send

import dev.mestizos.client.sri.Send
import dev.mestizos.roqui.util.DateUtil
import dev.mestizos.roqui.util.FilesUtil
import recepcion.ws.sri.gob.ec.Comprobante
import recepcion.ws.sri.gob.ec.Mensaje
import recepcion.ws.sri.gob.ec.RespuestaSolicitud
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI

class SendXML(
    private val accessKey: String,
    private val baseDirectory: String,
    private val webService: WebService
) {
    fun send(): RespuestaSolicitud {
        val dateAccessKey = DateUtil.accessKeyToDate(accessKey)

        val ambientType = getAmbientType(accessKey)

        if (ambientType == AmbientType.PRODUCTION) {
            if (!isWebServiceAlive(webService.productionReception)) {
                return getErrorResponse(webService.productionReception)
            }
        } else {
            if (!isWebServiceAlive(webService.developmentReception)) {
                return getErrorResponse(webService.developmentReception)
            }
        }

        val pathSigned = FilesUtil
            .directory(
                baseDirectory + "${File.separatorChar}Signed",
                dateAccessKey
            )

        val statusSend = Send.execute(
            "$pathSigned${File.separatorChar}$accessKey.xml"
        )

        return statusSend


//        val statusCheck = Check.execute(accessKey)
//
//
//        return true
    }

    private fun getAmbientType(accessKey: String): AmbientType {
        return if (accessKey.substring(23, 24) == "2") {
            AmbientType.PRODUCTION
        } else {
            AmbientType.DEVELOPMENT
        }
    }

    private fun isWebServiceAlive(urlWebServices: String): Boolean {
        var c: HttpURLConnection? = null
        try {
            val u = URI(urlWebServices).toURL()
            c = u.openConnection() as HttpURLConnection
            c.requestMethod = "GET"
            c.inputStream
            if (c.responseCode == 200) {
                return true
            }
        } catch (e: IOException) {
            println("Error SRI web service connection : " + e.message)
            return false
        } finally {
            c?.disconnect()
        }
        return false
    }

    private fun getErrorResponse(webServiceUrl: String): RespuestaSolicitud {
        val response = RespuestaSolicitud()
        val receipt = Comprobante()
        receipt.claveAcceso = accessKey

        val message = Mensaje()
        message.mensaje = "Error web service connection $webServiceUrl"
        message.tipo = "ERROR"

        receipt.mensajes = Comprobante.Mensajes()

        receipt.mensajes.mensaje.add(message)

        response.comprobantes = RespuestaSolicitud.Comprobantes()
        response.comprobantes.comprobante.add(receipt)

        response.estado = "ERROR"
        return response
    }
}