package dev.mestizos.roqui.invoice.service

import dev.mestizos.roqui.invoice.model.ReportInvoice
import dev.mestizos.roqui.invoice.repository.IReportInvoiceRepository
import dev.mestizos.roqui.util.DateUtil
import org.springframework.stereotype.Service

@Service
class ReportInvoiceService {

    private val reportInvoiceRepository: IReportInvoiceRepository

    constructor(reportInvoiceRepository: IReportInvoiceRepository) {
        this.reportInvoiceRepository = reportInvoiceRepository
    }

    fun getInvoiceByDatesAndStatus(startDate: String, endDate: String, status: String)
            : MutableList<ReportInvoice> {

        val startDateForQuery = DateUtil.toDate(startDate)
        val endDateForQuery = DateUtil.toDate(endDate)

        println(startDateForQuery)
        println(endDateForQuery)

        return reportInvoiceRepository.findByDatesAndStatus(
            startDateForQuery,
            endDateForQuery,
            status
        )
    }
}