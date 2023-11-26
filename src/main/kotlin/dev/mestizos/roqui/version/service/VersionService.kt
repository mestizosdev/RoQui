package dev.mestizos.roqui.version.service

import dev.mestizos.roqui.version.repository.VersionRepository
import org.springframework.stereotype.Service

@Service
class VersionService {

    private val versionRepository: VersionRepository

    constructor(versionRepository: VersionRepository) {
        this.versionRepository = versionRepository
    }

    fun getVersion() = versionRepository.findById(1).get().versionDatabase!!
}