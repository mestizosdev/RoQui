package dev.mestizos.roqui.version.service

import dev.mestizos.roqui.version.model.Version
import dev.mestizos.roqui.version.repository.VersionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VersionService {

    val versionRepository: VersionRepository

    constructor(versionRepository: VersionRepository) {
        this.versionRepository = versionRepository
    }

    fun getVersion(): Version {
        val version = versionRepository.findById(1).get()
        return version
    }
}