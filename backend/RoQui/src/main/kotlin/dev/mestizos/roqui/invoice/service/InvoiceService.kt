package dev.mestizos.roqui.invoice.service

import dev.mestizos.roqui.invoice.dto.TributaryInformation
import dev.mestizos.roqui.invoice.repository.IInvoiceRepository
import dev.mestizos.roqui.taxpayer.repository.IEstablishmentsRepository
import dev.mestizos.roqui.taxpayer.repository.ITaxpayerRepository
import org.springframework.stereotype.Service

@Service
class InvoiceService {

    private val invoiceRepository: IInvoiceRepository
    private val taxPayerRepository: ITaxpayerRepository
    private val establishmentRepository: IEstablishmentsRepository

    constructor(
        invoiceRepository: IInvoiceRepository,
        taxPayerRepository: ITaxpayerRepository,
        establishmentRepository: IEstablishmentsRepository
    ) {
        this.invoiceRepository = invoiceRepository
        this.taxPayerRepository = taxPayerRepository
        this.establishmentRepository = establishmentRepository
    }

    fun getInvoice(code: String, number: String): TributaryInformation {
        val invoice = invoiceRepository.findByCodeAndNumber(code, number)
        val taxpayer = taxPayerRepository.findById(1).get()
        val establishment = establishmentRepository.findByCode(invoice.establishment!!)
        val principalEstablishmentAddress = establishmentRepository.findPrincipal().address

        val tributaryInformation = TributaryInformation(
            invoice,
            taxpayer,
            establishment.address,
            principalEstablishmentAddress,
            establishment.businessName
        )

        return tributaryInformation
    }
}