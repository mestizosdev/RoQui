package dev.mestizos.roqui.electronic.send

import dev.mestizos.client.sri.Check
import dev.mestizos.client.sri.Send
import dev.mestizos.roqui.util.DateUtil
import dev.mestizos.roqui.util.FilesUtil
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI

class SendXML(
    private val accessKey: String,
    private val baseDirectory: String,
    private val webService: WebService
) {
    fun send(): Boolean {
        val dateAccessKey = DateUtil.accessKeyToDate(accessKey)
//        TODO()
 //     Verify if the web service is alive
//        Verify developer or production environment in access key

        val pathSigned = FilesUtil
            .directory(
                baseDirectory + "${File.separatorChar}Signed",
                dateAccessKey
            )

//        val statusSend = Send.execute(
//            "$pathSigned${File.separatorChar}$accessKey.xml"
//        )
//
//        val statusCheck = Check.execute(accessKey)


        return true
    }
    private fun isWebServiceAlive(urlWebServices : String) : Boolean {
        var c: HttpURLConnection? = null
        try {
            val u = URI(urlWebServices).toURL()
            c = u.openConnection() as HttpURLConnection
            c.requestMethod = "GET"
            c.inputStream
            if (c.responseCode == 200) {
                return true
            }
        } catch (e: IOException) {
            println("Error SRI web service connection : " + e.message)
            return false
        } finally {
            c?.disconnect()
        }
        return false
    }
}