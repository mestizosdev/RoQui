package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.model.Invoice
import dev.mestizos.roqui.invoice.model.InvoiceDetail

interface IInvoiceRepository {

    fun findByCodeAndNumber(code: String, number: String): Invoice
    fun findDetailByCodeAndNumber(code: String, number: String): MutableList<InvoiceDetail>
}