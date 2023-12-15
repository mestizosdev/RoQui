package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.dto.TaxTotal
import dev.mestizos.roqui.invoice.model.Invoice
import dev.mestizos.roqui.invoice.model.InvoiceDetail
import dev.mestizos.roqui.invoice.model.Payment
import dev.mestizos.roqui.invoice.model.TaxDetail

interface IInvoiceRepository {

    fun countByCodeAndNumber(code: String, number: String): Long
    fun findByCodeAndNumber(code: String, number: String): Invoice
    fun findDetailByCodeAndNumber(code: String, number: String): MutableList<InvoiceDetail>
    fun findDetailTax(
        code: String,
        number: String,
        principalCode: String,
        line: Long
    ): MutableList<TaxDetail>

    fun findTotalTaxByCodeAndNumber(code: String, number: String): MutableList<TaxTotal>
    fun findPaymentByCodeAndNumber(code: String, number: String): MutableList<Payment>
}