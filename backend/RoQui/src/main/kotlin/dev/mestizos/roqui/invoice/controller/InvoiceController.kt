package dev.mestizos.roqui.invoice.controller

import dev.mestizos.roqui.electronic.ElectronicDocument
import dev.mestizos.roqui.electronic.TypeDocument
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.util.dto.DocumentDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roqui/v1")
class InvoiceController {

    @Autowired
    lateinit var invoiceService: InvoiceService

    @PostMapping("/invoice/authorize")
    fun postAuthorize(@RequestBody document: DocumentDto): ResponseEntity<Any> {

        if (invoiceService.count(document.code, document.number) == 0L) {
            return ResponseEntity.notFound().build()
        }

        val buildInvoice = ElectronicDocument(
            document.code,
            document.number,
            invoiceService
        )
        buildInvoice.send(TypeDocument.FACTURA)
        return ResponseEntity.ok().build()
    }
}