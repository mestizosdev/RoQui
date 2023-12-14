package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.model.Invoice
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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
}