package dev.mestizos.roqui.electronic.service

import dev.mestizos.roqui.electronic.model.Document
import dev.mestizos.roqui.electronic.repository.IDocumentRepository
import org.springframework.stereotype.Service

@Service
class DocumentService(private val documentRepository: IDocumentRepository) {

    fun saveDocument(document: Document) {
        documentRepository.saveDocument(document)
    }

    fun getByCodeAndNumber(code: String, number: String): Document {
        return documentRepository.findByCodeAndNumber(code, number)
    }
}