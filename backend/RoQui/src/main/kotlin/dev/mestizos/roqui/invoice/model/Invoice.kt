package dev.mestizos.roqui.invoice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.math.BigDecimal
import java.util.*

@Entity
@Immutable
@Table(name = "v_ele_invoices")
class Invoice {

    @Id
    val id: Long? = null

    @Column(name = "code")
    val code: String? = null

    @Column(name = "number")
    val number: String? = null

    @Column(name = "code_document")
    val codeDocument: String? = null

    @Column(name = "establishment")
    val establishment: String? = null

    @Column(name = "emission_point")
    val emissionPoint: String? = null

    @Column(name = "sequence")
    val sequence: String? = null

    @Column(name = "date", columnDefinition = "DATE")
    val date: Date? = null

    @Column(name = "total")
    val total: BigDecimal? = null

    @Column(name = "identification_type")
    val identificationType: String? = null

    @Column(name = "identification")
    val identification: String? = null

    @Column(name = "legal_name")
    val legalName: String? = null

    @Column(name = "address")
    val address: String? = null

    @Column(name = "delivery_note")
    val deliveryNote: String? = null

    @Column(name = "establishment_address")
    val establishmentAddress: String? = null
}