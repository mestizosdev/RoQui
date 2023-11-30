package dev.mestizos.roqui.invoice.dto

import java.math.BigDecimal
import java.util.Date

data class ReportInvoiceDto(
    val id: Long? = null,
    val code: String? = null,
    val number: String? = null,
    val date: Date? = null,
    val total: BigDecimal? = null,
    val identification: String? = null,
    val legal_name: String? = null,
    val email: String? = null,
    val status: String? = null
)