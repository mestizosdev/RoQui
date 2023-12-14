package dev.mestizos.roqui.taxpayer.repository

import dev.mestizos.roqui.taxpayer.model.Establishments

interface IEstablishmentsRepository {

    fun finbByCode(code: String): Establishments
}