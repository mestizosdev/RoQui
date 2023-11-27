package dev.mestizos.roqui.invoice.controller

import dev.mestizos.roqui.invoice.model.ReportInvoice
import dev.mestizos.roqui.invoice.service.ReportInvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roqui/v1")
class ReportInvoiceRest {

    @Autowired
    lateinit var reportInvoiceService: ReportInvoiceService

    @GetMapping("/report/invoice/dates/{startDate}/{endDate}/status/{status}")
    fun getInvoiceByDatesAndStatus(
        @PathVariable(value = "startDate") startDate: String,
        @PathVariable(value = "endDate") endDate: String,
        @PathVariable(value = "status") status: String
    )
            : ResponseEntity<MutableList<ReportInvoice>> {

        val reportInvoice = reportInvoiceService.getInvoiceByDatesAndStatus(startDate, endDate, status)

        return ResponseEntity.ok().body(mutableListOf())
    }
}