package dev.mestizos.roqui.version.controller

import dev.mestizos.roqui.version.service.VersionService
import org.apache.commons.lang3.SystemUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class Version {

    @Autowired
    lateinit var versionService: VersionService

    @GetMapping("/version")
    fun getVersion() = Application(versionService.getVersion().versionDatabase)


    class Application(versionDatabase: String?) {
        val application = Properties(versionDatabase)
    }

    class Properties(versionDatabase: String?) {
        val name = "Roqui E-Invoicing for Ecuador"
        val author = "Jorge Luis"
        val versionOS: String = SystemUtils.OS_NAME + " " + SystemUtils.OS_VERSION + " " + SystemUtils.OS_ARCH
        val versionJava: String = SystemUtils.JAVA_VERSION
        var versionDatabase: String? = versionDatabase
        val version = "0.0.1"
    }
}