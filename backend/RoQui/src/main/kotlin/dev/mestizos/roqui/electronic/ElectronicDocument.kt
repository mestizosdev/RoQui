package dev.mestizos.roqui.electronic

import dev.mestizos.roqui.electronic.model.Document
import dev.mestizos.roqui.electronic.send.SendXML
import dev.mestizos.roqui.electronic.send.WebService
import dev.mestizos.roqui.electronic.service.DocumentService
import dev.mestizos.roqui.electronic.sign.SignerXml
import dev.mestizos.roqui.electronic.xml.BuildInvoice
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.parameter.service.ParameterService
import recepcion.ws.sri.gob.ec.Comprobante
import recepcion.ws.sri.gob.ec.RespuestaSolicitud
import java.time.LocalDateTime

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

        return ""
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

        if (documentService.getByCodeAndNumber(code, number).id == null){
            val document = Document(code, number, message, response.estado)
            documentService.saveDocument(document)
        }
        else {
            val document = documentService.getByCodeAndNumber(code, number)

            document.observation = message + " | " + document.observation
            document.status = response.estado
            documentService.saveDocument(document)
        }

        return response.estado
    }
}