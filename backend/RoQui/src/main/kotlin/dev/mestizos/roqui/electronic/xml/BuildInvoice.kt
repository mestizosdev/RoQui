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

    private val tributaryInformation = invoiceService.getInvoiceAndTaxpayer(code, number)

    fun xml(): String {
        val factura = Factura()
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

            marshaller.marshal(factura, out)
            println(stringWriter)
        } catch (e: Exception) {
            println(e.message)
        }
        return ""
    }

    private fun buildInfoTributaria(): InfoTributaria {
        val informacionTributaria = InfoTributaria()

        informacionTributaria.ruc = tributaryInformation.taxpayer.identification
        informacionTributaria.razonSocial = tributaryInformation.taxpayer.legalName
        informacionTributaria.nombreComercial = tributaryInformation.establishmentBusinessName

        if (tributaryInformation.invoice.accessKey!!.length == 49) {
            informacionTributaria.claveAcceso = tributaryInformation.invoice.accessKey
            informacionTributaria.ambiente = informacionTributaria.claveAcceso.substring(23, 24)
            informacionTributaria.tipoEmision = informacionTributaria.claveAcceso.substring(39, 40)
        }

        informacionTributaria.codDoc = tributaryInformation.invoice.codeDocument
        informacionTributaria.estab = tributaryInformation.invoice.establishment
        informacionTributaria.ptoEmi = tributaryInformation.invoice.emissionPoint
        informacionTributaria.secuencial = tributaryInformation.invoice.sequence
        informacionTributaria.dirMatriz = tributaryInformation.principalEstablishmentAddress
        informacionTributaria.contribuyenteRimpe = tributaryInformation.taxpayer.rimpe
        informacionTributaria.agenteRetencion = tributaryInformation.taxpayer.retentionAgent

        return informacionTributaria
    }
}