package dev.mestizos.roqui.information.repository

import dev.mestizos.roqui.information.model.Information

interface IInformationRepository {
    fun findInformationByIdentification(identification: String): MutableList<Information>
}