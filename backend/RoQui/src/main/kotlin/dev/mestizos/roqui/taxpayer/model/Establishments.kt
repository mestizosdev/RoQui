package dev.mestizos.roqui.taxpayer.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Immutable
@Table(name = "v_ele_establishments")
class Establishments {

    @Id
    val id: Long? = null

    @Column(name = "identification")
    val identification: String? = null

    @Column(name = "code")
    val code: String? = null

    @Column(name = "comercial_name")
    val comercialName: String? = null

    @Column(name = "address")
    val address: String? = null
}