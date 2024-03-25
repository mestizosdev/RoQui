package dev.mestizos.roqui.parameter.service

import dev.mestizos.roqui.parameter.repository.IParameterRepository
import org.springframework.stereotype.Service

@Service
class ParameterService(
    private val parameterRepository: IParameterRepository
) {

    fun getBaseDirectory(): String {
        return parameterRepository.findValueByName("Base Directory")
    }

    fun getCertificatePath(): String {
        return parameterRepository.findValueByName("Certificate")
    }

    fun getCertificatePassword(): String {
        return parameterRepository.findValueByName("Certificate Password")
    }

    fun getPathLogo(): String {
        return parameterRepository.findValueByName("Logo")
    }
}