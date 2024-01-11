package dev.mestizos.roqui.electronic

import dev.mestizos.roqui.electronic.send.SendXML
import dev.mestizos.roqui.electronic.send.WebService
import dev.mestizos.roqui.electronic.sign.SignerXml
import dev.mestizos.roqui.electronic.xml.BuildInvoice
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.parameter.service.ParameterService

class ElectronicDocument(
    val code: String,
    val number: String,
    private val invoiceService: InvoiceService,
    private val webService: WebService,
    private val parameterService: ParameterService
) {

    private var accessKey: String = ""

    fun process(type: TypeDocument): String {
        val baseDirectory = parameterService.getBaseDirectory()

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
            xml.send()
            return accessKey
        }

        return ""
    }
}