package dev.mestizos.roqui.taxpayer.controller

import dev.mestizos.roqui.taxpayer.service.TaxpayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roqui/v1")
class TaxpayerController {

    @Autowired
    lateinit var taxpayerService: TaxpayerService

    @GetMapping("/taxpayer")
    fun getTaxpayer() = taxpayerService.getTaxpayer()
}