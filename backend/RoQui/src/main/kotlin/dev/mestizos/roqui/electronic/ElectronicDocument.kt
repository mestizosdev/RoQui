package dev.mestizos.roqui.electronic

import dev.mestizos.roqui.electronic.sign.SignerXml
import dev.mestizos.roqui.electronic.xml.BuildInvoice
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.parameter.service.ParameterService

class ElectronicDocument(
    val code: String,
    val number: String,
    private val invoiceService: InvoiceService,
    private val parameterService: ParameterService
) {

    private var accessKey: String = ""

    fun process(type: TypeDocument): String {
        if (type == TypeDocument.FACTURA) {
            val build = BuildInvoice(code, number, invoiceService)
            accessKey = build.xml()
        }

        val signer = SignerXml(accessKey, parameterService)
        signer.sign()
        return ""
    }

}