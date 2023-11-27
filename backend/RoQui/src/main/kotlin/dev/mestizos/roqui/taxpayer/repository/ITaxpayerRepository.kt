package dev.mestizos.roqui.taxpayer.repository

import dev.mestizos.roqui.taxpayer.model.Taxpayer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ITaxpayerRepository : JpaRepository<Taxpayer, Long>
