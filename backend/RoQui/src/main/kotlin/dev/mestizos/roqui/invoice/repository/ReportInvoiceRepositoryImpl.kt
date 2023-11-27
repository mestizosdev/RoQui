package dev.mestizos.roqui.invoice.repository

import dev.mestizos.roqui.invoice.model.ReportInvoice
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@Repository
class ReportInvoiceRepositoryImpl : IReportInvoiceRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findByDatesAndStatus(startDate: Date, endDate: Date, status: String)
            : MutableList<ReportInvoice> {

        val resultQuery = if (status.equals("Authorized")) {
            return entityManager.createQuery(
                "from ReportInvoice " +
                        "where date between :startDate and :endDate " +
                        "and status = 'AUTORIZADO'"
            )
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .resultList as MutableList<ReportInvoice>
        } else {
            return entityManager.createQuery(
                "from ReportInvoice " +
                        "where date between :startDate and :endDate " +
                        "and status != 'AUTORIZADO'"
            )
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .resultList as MutableList<ReportInvoice>
        }

        resultQuery.
        return resultQuery
    }
}