package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.dto.TaxTotal
import dev.mestizos.roqui.invoice.model.Invoice
import dev.mestizos.roqui.invoice.model.InvoiceDetail
import dev.mestizos.roqui.invoice.model.TaxDetail
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Transactional
@Repository
class InvoiceRepositoryImpl : IInvoiceRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findByCodeAndNumber(code: String, number: String): Invoice {
        val invoice = entityManager.createQuery(
            "from Invoice " +
                    "where code = :code " +
                    "and number = :number"
        )
            .setParameter("code", code)
            .setParameter("number", number)
            .resultList.get(0) as Invoice

        return invoice
    }

    override fun findDetailByCodeAndNumber(code: String, number: String): MutableList<InvoiceDetail> {
        val invoiceDetail = entityManager.createQuery(
            "from InvoiceDetail " +
                    "where code = :code " +
                    "and number = :number"
        )
            .setParameter("code", code)
            .setParameter("number", number)
            .resultList as MutableList<InvoiceDetail>

        return invoiceDetail
    }

    override fun findDetailTax(
        code: String,
        number: String,
        principalCode: String,
        line: Long
    ): MutableList<TaxDetail> {
        val taxDetail = entityManager.createQuery(
            "from TaxDetail " +
                    "where code = :code " +
                    "and number = :number " +
                    "and principalCode = :principalCode " +
                    "and line = :line"
        )
            .setParameter("code", code)
            .setParameter("number", number)
            .setParameter("principalCode", principalCode)
            .setParameter("line", line)
            .resultList as MutableList<TaxDetail>

        return taxDetail
    }

    override fun findTaxByCodeAndNumber(code: String, number: String): MutableList<TaxTotal> {
        val taxTotalResult = entityManager.createQuery(
            "SELECT taxCode, percentageCode, taxIva, sum(taxBase) as taxBase, sum(value) as value from TaxDetail " +
                    "where code = :code " +
                    "and number = :number " +
                    "group by taxCode, percentageCode, taxIva"
        )
            .setParameter("code", code)
            .setParameter("number", number)
            .resultList

        val taxTotal = mutableListOf<TaxTotal>()

        for (i in taxTotalResult.indices) {
            val row = taxTotalResult[i] as Array<Any>
            val tax = TaxTotal(
                row[0] as String,
                row[1] as String,
                row[2] as BigDecimal,
                row[3] as BigDecimal,
                row[4] as BigDecimal
            )

            taxTotal.add(tax)
        }

        return taxTotal
    }
}