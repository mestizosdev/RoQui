package dev.mestizos.roqui.electronic

import dev.mestizos.roqui.electronic.xml.BuildInvoice
import dev.mestizos.roqui.invoice.service.InvoiceService

class ElectronicDocument(
    val code: String,
    val number: String,
    val invoiceService: InvoiceService
) {

    fun send(type: TypeDocument): String {
        if (type == TypeDocument.FACTURA) {
            val build = BuildInvoice(code, number, invoiceService)
            return build.xml()
        }
        return ""
    }

}