package dev.mestizos.roqui.electronic.xml

import dev.mestizos.roqui.invoice.service.InvoiceService
import dev.mestizos.roqui.util.FilesUtil
import ec.gob.sri.invoice.v210.Factura
import ec.gob.sri.invoice.v210.InfoTributaria
import ec.gob.sri.invoice.v210.ObligadoContabilidad
import java.io.StringWriter
import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.math.BigDecimal
import java.text.SimpleDateFormat

class BuildInvoice(
    val code: String,
    val number: String,
    invoiceService: InvoiceService
) {

    private val tributaryInformation = invoiceService.getInvoiceAndTaxpayer(code, number)
    private val baseDirectory = invoiceService.getBaseDirectory()

    fun xml(): String {
        val factura = Factura()

        try {
            factura.id = "comprobante"
            factura.version = "2.1.0"
            factura.infoTributaria = buildInfoTributaria()
            factura.infoFactura = buildInfoFactura()

            val jaxbContext = JAXBContext.newInstance(Factura::class.java)
            val marshaller = jaxbContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            marshaller.setProperty("jaxb.encoding", "UTF-8")

            val stringWriter = StringWriter()
            stringWriter.use {
                marshaller.marshal(factura, stringWriter)
            }

            val pathGenerated = FilesUtil
                .directory(
                    baseDirectory + "${File.separatorChar}Generated",
                    tributaryInformation.invoice.date!!
                )

            val out = OutputStreamWriter(
                FileOutputStream(
                    "$pathGenerated${File.separatorChar}" +
                            "${factura.infoTributaria.claveAcceso}.xml"
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
        val infoTributaria = InfoTributaria()

        infoTributaria.ruc = tributaryInformation.taxpayer.identification
        infoTributaria.razonSocial = tributaryInformation.taxpayer.legalName
        infoTributaria.nombreComercial = tributaryInformation.establishmentBusinessName

        if (tributaryInformation.invoice.accessKey!!.length == 49) {
            infoTributaria.claveAcceso = tributaryInformation.invoice.accessKey
            infoTributaria.ambiente = infoTributaria.claveAcceso.substring(23, 24)
            infoTributaria.tipoEmision = infoTributaria.claveAcceso.substring(39, 40)
        }

        infoTributaria.codDoc = tributaryInformation.invoice.codeDocument
        infoTributaria.estab = tributaryInformation.invoice.establishment
        infoTributaria.ptoEmi = tributaryInformation.invoice.emissionPoint
        infoTributaria.secuencial = tributaryInformation.invoice.sequence
        infoTributaria.dirMatriz = tributaryInformation.principalEstablishmentAddress
        infoTributaria.contribuyenteRimpe = tributaryInformation.taxpayer.rimpe
        infoTributaria.agenteRetencion = tributaryInformation.taxpayer.retentionAgent

        return infoTributaria
    }
    private fun buildInfoFactura(): Factura.InfoFactura {
        val infoFactura = Factura.InfoFactura()

        infoFactura.fechaEmision = SimpleDateFormat("dd/MM/yyyy").format(tributaryInformation.invoice.date)
        infoFactura.dirEstablecimiento = tributaryInformation.establishmentAddress
        infoFactura.contribuyenteEspecial = tributaryInformation.taxpayer.specialTaxpayer
        if (tributaryInformation.taxpayer.forcedAccounting == "SI") {
            infoFactura.obligadoContabilidad = ObligadoContabilidad.SI
        } else {
            infoFactura.obligadoContabilidad = ObligadoContabilidad.NO
        }
        infoFactura.tipoIdentificacionComprador = tributaryInformation.invoice.identificationType
        infoFactura.identificacionComprador = tributaryInformation.invoice.identification
        infoFactura.razonSocialComprador = tributaryInformation.invoice.legalName
        infoFactura.direccionComprador = tributaryInformation.invoice.address
        infoFactura.guiaRemision = tributaryInformation.invoice.deliveryNote
        infoFactura.totalSinImpuestos = tributaryInformation.invoice.totalWithoutTaxes!!.setScale(2, BigDecimal.ROUND_HALF_UP)
        infoFactura.importeTotal = tributaryInformation.invoice.total!!.setScale(2, BigDecimal.ROUND_HALF_UP)
        infoFactura.propina = BigDecimal(0).setScale(2)
        infoFactura.totalDescuento = BigDecimal(0).setScale(2)
        infoFactura.moneda = "DOLAR"

        return infoFactura
    }
}
