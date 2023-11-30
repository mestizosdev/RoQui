package dev.mestizos.roqui.invoice.controller

import dev.mestizos.roqui.util.dto.DocumentDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roqui/v1")
class AuthorizeInvoiceRest {

    @PostMapping("/invoice/authorize")
    fun postAuthorize(@RequestBody document: DocumentDto): ResponseEntity<Any> {
        println(document.code)
        println(document.number)
        if (document.code == null || document.number == null) {
            return ResponseEntity.badRequest().build()
        }
        else{

        }
        return ResponseEntity.ok().build()
    }
}