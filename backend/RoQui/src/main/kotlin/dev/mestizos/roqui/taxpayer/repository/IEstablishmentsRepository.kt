package dev.mestizos.roqui.taxpayer.repository

import dev.mestizos.roqui.taxpayer.model.Establishment

interface IEstablishmentsRepository {

    fun findByCode(code: String): Establishment
    fun findPrincipal(): Establishment
}