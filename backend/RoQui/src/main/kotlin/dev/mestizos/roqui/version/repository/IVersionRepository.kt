package dev.mestizos.roqui.version.repository

import dev.mestizos.roqui.version.model.Version
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IVersionRepository : JpaRepository<Version, Long>