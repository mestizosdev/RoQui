package dev.mestizos.roqui.taxpayer.service

import dev.mestizos.roqui.taxpayer.dto.TaxpayerDto
import dev.mestizos.roqui.taxpayer.mapper.TaxpayerMapperImpl
import dev.mestizos.roqui.taxpayer.repository.TaxpayerRepository
import org.springframework.stereotype.Service

@Service
class TaxpayerService {

    private val taxPayerRepository: TaxpayerRepository

    constructor(taxPayerRepository: TaxpayerRepository) {
        this.taxPayerRepository = taxPayerRepository
    }

    fun getTaxpayer(): TaxpayerDto {
        val taxpayer = taxPayerRepository.findById(1).get()
        val taxpayerMapper= TaxpayerMapperImpl()
        return taxpayerMapper.toDto(taxpayer)
    }
}