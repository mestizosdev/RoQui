package dev.mestizos.roqui.invoice.service

import dev.mestizos.roqui.invoice.dto.TaxTotal
import dev.mestizos.roqui.invoice.dto.TributaryInformation
import dev.mestizos.roqui.invoice.model.InvoiceDetail
import dev.mestizos.roqui.invoice.model.Payment
import dev.mestizos.roqui.invoice.model.TaxDetail
import dev.mestizos.roqui.invoice.repository.IInvoiceRepository
import dev.mestizos.roqui.parameter.repository.IParameterRepository
import dev.mestizos.roqui.taxpayer.repository.IEstablishmentsRepository
import dev.mestizos.roqui.taxpayer.repository.ITaxpayerRepository
import org.springframework.stereotype.Service

@Service
class InvoiceService(
    private val invoiceRepository: IInvoiceRepository,
    private val taxPayerRepository: ITaxpayerRepository,
    private val establishmentRepository: IEstablishmentsRepository,
    private val parameterRespository: IParameterRepository
) {

    fun getInvoiceAndTaxpayer(code: String, number: String): TributaryInformation {
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

    fun getInvoiceDetail(code: String, number: String): MutableList<InvoiceDetail> {
        return invoiceRepository.findDetailByCodeAndNumber(code, number)
    }

    fun getInvoiceDetailTax(
        code: String,
        number: String,
        principalCode: String,
        line: Long
    ): MutableList<TaxDetail> {
        return invoiceRepository.findDetailTax(code, number, principalCode, line)
    }

    fun getInvoiceTax(code: String, number: String): MutableList<TaxTotal> {
        return invoiceRepository.findTotalTaxByCodeAndNumber(code, number)
    }

    fun getInvoicePayment(code: String, number: String): MutableList<Payment> {
        return invoiceRepository.findPaymentByCodeAndNumber(code, number)
    }

    fun getBaseDirectory(): String {
        return parameterRespository.findValueByName("Base Directory")
    }
}