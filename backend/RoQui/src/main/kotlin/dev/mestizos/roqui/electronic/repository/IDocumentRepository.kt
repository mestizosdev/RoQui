package dev.mestizos.roqui.electronic.repository

import dev.mestizos.roqui.electronic.model.Document

interface IDocumentRepository {

    fun findByCodeAndNumber(code: String, number: String): Document
    fun saveDocument(document: Document)
}