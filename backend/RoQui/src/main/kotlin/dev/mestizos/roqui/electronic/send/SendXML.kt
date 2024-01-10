package dev.mestizos.roqui.electronic.send

import dev.mestizos.roqui.parameter.service.ParameterService
import dev.mestizos.roqui.util.DateUtil
import dev.mestizos.roqui.util.FilesUtil
import dev.mestizos.client.sri.Send
import java.io.File

class SendXML(
    private val accessKey: String,
    private val parameterService: ParameterService
) {

        fun send(): Boolean {
            val dateAccessKey = DateUtil.accessKeyToDate(accessKey)

            val baseDirectory = parameterService.getBaseDirectory()

            val pathSigned = FilesUtil
                .directory(
                    baseDirectory + "${File.separatorChar}Signed",
                    dateAccessKey
                )

            val statusSend = Send.execute(
                "$pathSigned${File.separatorChar}$accessKey.xml"
            )

            return true
        }
}