package dev.mestizos.roqui.parameter.repository

interface IParameterRepository {

    fun findValueByName(name: String): String
}