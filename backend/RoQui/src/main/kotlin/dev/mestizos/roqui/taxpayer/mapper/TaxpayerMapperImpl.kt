package dev.mestizos.roqui.taxpayer.mapper

import dev.mestizos.roqui.taxpayer.dto.TaxpayerDto
import dev.mestizos.roqui.taxpayer.model.Taxpayer

class TaxpayerMapperImpl : TaxpayerMapper {

    override fun toDto(taxpayerEntity: Taxpayer): TaxpayerDto {
        return TaxpayerDto(
            id = taxpayerEntity.id,
            identification = taxpayerEntity.identification,
            legalName = taxpayerEntity.legalName,
            forcedAccounting = taxpayerEntity.forcedAccounting,
            specialTaxpayer = taxpayerEntity.specialTaxpayer,
            retentionAgent = taxpayerEntity.retentionAgent,
            rimpe = taxpayerEntity.rimpe
        )
    }
}