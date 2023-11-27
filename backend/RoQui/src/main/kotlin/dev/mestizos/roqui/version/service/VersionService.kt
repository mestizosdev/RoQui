package dev.mestizos.roqui.version.service

import dev.mestizos.roqui.version.repository.IVersionRepository
import org.springframework.stereotype.Service

@Service
class VersionService {

    private val versionRepository: IVersionRepository

    constructor(versionRepository: IVersionRepository) {
        this.versionRepository = versionRepository
    }

    fun getVersion() = versionRepository.findById(1).get().versionDatabase!!
}