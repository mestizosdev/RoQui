package dev.mestizos.roqui.electronic.sign

import dev.mestizos.roqui.parameter.service.ParameterService
import dev.mestizos.roqui.signer.Signer
import dev.mestizos.roqui.util.DateUtil
import dev.mestizos.roqui.util.FilesUtil
import java.io.File

class SignerXml(
    private val accessKey: String,
    private val parameterService: ParameterService
) {

    fun sign(): Boolean {
        val dateAccessKey = DateUtil.accessKeyToDate(accessKey)

        val path = parameterService.getCertificatePath()
        val password = parameterService.getCertificatePassword()

        val baseDirectory = parameterService.getBaseDirectory()

        val pathGenerated = FilesUtil
            .directory(
                baseDirectory + "${File.separatorChar}Generated",
                dateAccessKey
            )
        val pathSigned = FilesUtil
            .directory(
                baseDirectory + "${File.separatorChar}Signed",
                dateAccessKey
            )

        val signer = Signer()
        signer.sign(
            path,
            password,
            "$pathGenerated${File.separatorChar}$accessKey.xml",
            "$pathSigned${File.separatorChar}$accessKey.xml"
        )

        return true
    }
}