package dev.mestizos.roqui.invoice.dto

import dev.mestizos.roqui.invoice.model.Invoice
import dev.mestizos.roqui.taxpayer.model.Taxpayer

data class TributaryInformation(
    val invoice: Invoice,
    val taxpayer: Taxpayer,
    val establishmentAddress: String? = null,
    val establishmentComercialName: String? = null
)