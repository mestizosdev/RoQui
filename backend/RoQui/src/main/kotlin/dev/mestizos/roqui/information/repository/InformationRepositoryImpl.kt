package dev.mestizos.roqui.information.repository

import dev.mestizos.roqui.information.model.Information
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional
@Repository
class InformationRepositoryImpl : IInformationRepository {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    override fun findInformationByIdentification(identification: String): MutableList<Information> {
        val information = entityManager.createQuery(
            "from Information " +
                    "where identification = :identification"
        )
            .setParameter("identification", identification)
            .resultList as MutableList<Information>

        return information
    }
}