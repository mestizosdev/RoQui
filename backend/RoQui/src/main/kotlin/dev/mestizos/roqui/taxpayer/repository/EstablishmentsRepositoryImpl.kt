package dev.mestizos.roqui.taxpayer.repository

import dev.mestizos.roqui.taxpayer.model.Establishments
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class EstablishmentsRepositoryImpl : IEstablishmentsRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun finbByCode(code: String): Establishments {
        return entityManager.createQuery(
            "from Establishments " +
                    "where code = :code "
        )
            .setParameter("code", code)
            .resultList.get(0) as Establishments
    }
}