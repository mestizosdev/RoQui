package dev.mestizos.roqui.electronic

import dev.mestizos.definition.AutorizacionEstado
import dev.mestizos.roqui.electronic.model.Document
import dev.mestizos.roqui.electronic.send.SendXML
import dev.mestizos.roqui.electronic.send.WebService
import dev.mestizos.roqui.electronic.service.DocumentService
import dev.mestizos.roqui.electronic.sign.SignerXml
import dev.mestizos.roqui.electronic.xml.BuildInvoice
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.parameter.service.ParameterService
import dev.mestizos.roqui.util.DateUtil
import recepcion.ws.sri.gob.ec.Comprobante
import recepcion.ws.sri.gob.ec.RespuestaSolicitud
import java.time.LocalDateTime
import kotlin.NoSuchElementException

class ElectronicDocument(
    val code: String,
    val number: String,
    private val invoiceService: InvoiceService,
    private val webService: WebService,
    private val parameterService: ParameterService,
    private val documentService: DocumentService
) {

    private var accessKey: String = ""

    fun process(type: TypeDocument): String {
        val baseDirectory = parameterService.getBaseDirectory()
        var statusResponse = ""

        if (type == TypeDocument.FACTURA) {
            val build = BuildInvoice(code, number, baseDirectory, invoiceService)
            accessKey = build.xml()
        }

        if (accessKey.isEmpty()) {
            return ""
        }

        val certificatePath = parameterService.getCertificatePath()
        val certificatePassword = parameterService.getCertificatePassword()

        val signer = SignerXml(accessKey, baseDirectory, certificatePath, certificatePassword)

        if (signer.sign()) {
            val xml = SendXML(accessKey, baseDirectory, webService)
            val response = xml.send()


            response?.let {
                statusResponse = saveResponse(it)
            }

            return accessKey
        }

        return statusResponse
    }

    fun check() {
        val baseDirectory = parameterService.getBaseDirectory()
        val xml = SendXML(accessKey, baseDirectory, webService)
        val response = xml.check()

        saveResponse(response)
    }

    private fun saveResponse(response: AutorizacionEstado): String {
        var message = ""

        if (response.autorizacion.mensajes != null) {
            for (i in response.autorizacion.mensajes.mensaje.indices) {
                val messageResponse = response.autorizacion.mensajes.mensaje[i]
                if (messageResponse.mensaje != null) {
                    message = "$message ${messageResponse.mensaje} ${messageResponse.informacionAdicional}"
                }
            }
        }

        try {
            val document = documentService.getByCodeAndNumber(code, number)

            if (document.observation!!.length > 2400){
                document.observation = ""
            }

            document.observation = "$message ${document.observation}"

            if (response.autorizacion.fechaAutorizacion != null) {
               document.observation = " | ${response.autorizacion.numeroAutorizacion} " +
                       "${response.autorizacion.fechaAutorizacion} ${document.observation}"

               document.authorization = response.autorizacion.numeroAutorizacion

               document.authorizationDate = DateUtil.extractDate(
                   response.autorizacion.fechaAutorizacion
               )
            }
            document.status = response.autorizacion.estado

            documentService.saveDocument(document)
        } catch (e: NoSuchElementException){
            val document = Document(code, number, message, response.autorizacion.estado)

            if (response.autorizacion.fechaAutorizacion != null) {
                document.observation = " | ${response.autorizacion.numeroAutorizacion} " +
                        "${response.autorizacion.fechaAutorizacion} ${document.observation}"

                document.authorization = response.autorizacion.numeroAutorizacion

                document.authorizationDate = DateUtil.extractDate(
                    response.autorizacion.fechaAutorizacion
                )
            }
            documentService.saveDocument(document)
        }
        return response.autorizacion.estado
    }

    private fun saveResponse(response: RespuestaSolicitud): String {
        var receipt: Comprobante
        val date = LocalDateTime.now()
        var message = "$date |"

        if (response.comprobantes == null) {
            return ""
        }

        if (response.comprobantes.comprobante.size > 0) {
            for (i in response.comprobantes.comprobante.indices) {
                receipt = response.comprobantes.comprobante[i] as Comprobante

                for (m in receipt.mensajes.mensaje.indices) {
                    val messageReceipt = receipt.mensajes.mensaje[m]
                    if (messageReceipt.mensaje != null) {
                        message = message + " " +
                                messageReceipt.mensaje + " " +
                                messageReceipt.informacionAdicional
                    }
                }
                message += " "
            }
        }

        try {
            val document = documentService.getByCodeAndNumber(code, number)

            document.observation = message + " | " + document.observation
            document.status = response.estado
            documentService.saveDocument(document)
        } catch (e: NoSuchElementException){
            val document = Document(code, number, message, response.estado)
            documentService.saveDocument(document)
        }

        return response.estado
    }
}