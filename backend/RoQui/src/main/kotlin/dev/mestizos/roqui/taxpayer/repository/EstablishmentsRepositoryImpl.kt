package dev.mestizos.roqui.taxpayer.repository

import dev.mestizos.roqui.taxpayer.model.Establishment
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class EstablishmentsRepositoryImpl : IEstablishmentsRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findByCode(code: String): Establishment {
        return entityManager.createQuery(
            "from Establishment " +
                    "where code = :code "
        )
            .setParameter("code", code)
            .resultList.get(0) as Establishment
    }

    override fun findPrincipal(): Establishment {
        return entityManager.createQuery(
            "from Establishment " +
                    "where principal = 'Principal' "
        )
            .resultList.get(0) as Establishment
    }
}