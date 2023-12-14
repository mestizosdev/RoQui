package dev.mestizos.roqui.electronic.xml

import dev.mestizos.roqui.invoice.service.InvoiceService
import ec.gob.sri.invoice.v210.Factura
import ec.gob.sri.invoice.v210.InfoTributaria
import java.io.StringWriter
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class BuildInvoice(
    val code: String,
    val number: String,
    val invoiceService: InvoiceService
) {

    val factura = Factura()


    fun xml(): String {
        try {
            factura.id = "comprobante"
            factura.version = "2.1.0"
            factura.infoTributaria = buildInfoTributaria()

            val jaxbContext = JAXBContext.newInstance(Factura::class.java)
            val marshaller = jaxbContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            marshaller.setProperty("jaxb.encoding", "UTF-8")

            val stringWriter = StringWriter()
            stringWriter.use {
                marshaller.marshal(factura, stringWriter)
            }

            val out = OutputStreamWriter(
                FileOutputStream(
                    "/tmp/hola.xml"
                ), "UTF-8"
            )

            marshaller.marshal(this.factura, out)
            println(stringWriter)
        } catch (e: Exception) {
            println(e.message)
        }
        return ""
    }

    private fun buildInfoTributaria(): InfoTributaria {
        val informacionTributaria = InfoTributaria()

        println("buildInfoTributaria")
        val i = invoiceService.getInvoice(code, number)

        informacionTributaria.ruc = i.taxpayer.identification
        informacionTributaria.razonSocial = i.taxpayer.legalName
        informacionTributaria.nombreComercial = i.establishmentComercialName
        informacionTributaria.claveAcceso = i.invoice.accessKey


        return informacionTributaria
    }
}