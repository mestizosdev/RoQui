package dev.mestizos.roqui.taxpayer.mapper

import dev.mestizos.roqui.taxpayer.dto.TaxpayerDto
import dev.mestizos.roqui.taxpayer.model.Taxpayer
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface TaxpayerMapper {

//    @Mapping(source = "id", target = "id")
//    @Mapping(source = "identification", target = "identification")
//    @Mapping(source = "legalName", target = "legalName")
//    @Mapping(source = "forcedAccounting", target = "forcedAccounting")
//    @Mapping(source = "specialTaxpayer", target = "specialTaxpayer")
//    @Mapping(source = "retentionAgent", target = "retentionAgent")
//    @Mapping(source = "rimpe", target = "rimpe")
    fun toDto(taxpayerEntity: Taxpayer): TaxpayerDto
}