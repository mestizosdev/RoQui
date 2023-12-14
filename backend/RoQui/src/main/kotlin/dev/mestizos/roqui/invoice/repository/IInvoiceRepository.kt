package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.model.Invoice

interface IInvoiceRepository {

    fun findByCodeAndNumber(code: String, number: String): Invoice
}