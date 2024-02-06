package dev.mestizos.roqui.invoice.controller

import dev.mestizos.roqui.electronic.ElectronicDocument
import dev.mestizos.roqui.electronic.TypeDocument
import dev.mestizos.roqui.electronic.send.WebService
import dev.mestizos.roqui.electronic.service.DocumentService
import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.parameter.service.ParameterService
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

    @Autowired
    lateinit var parameterService: ParameterService

    @Autowired
    lateinit var documentService: DocumentService

    @Autowired
    lateinit var webService: WebService

    @PostMapping("/invoice/authorize")
    fun postAuthorize(@RequestBody document: DocumentDto): ResponseEntity<Any> {

        if (invoiceService.count(document.code, document.number) == 0L) {
            return ResponseEntity.notFound().build()
        }

        val buildInvoice = ElectronicDocument(
            document.code,
            document.number,
            invoiceService,
            webService,
            parameterService,
            documentService
        )

        buildInvoice.process(TypeDocument.FACTURA)
        return ResponseEntity.ok().build()
    }
}