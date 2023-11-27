package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.model.ReportInvoice
import java.util.*

interface IReportInvoiceRepository {

    fun findByDatesAndStatus(startDate: Date, endDate: Date, status: String)
            : MutableList<ReportInvoice>
}