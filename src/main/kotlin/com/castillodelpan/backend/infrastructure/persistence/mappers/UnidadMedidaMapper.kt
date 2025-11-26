package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.infrastructure.persistence.entities.core.UnidadMedidaData

object UnidadMedidaMapper {
    fun toDomain(data: UnidadMedidaData): UnidadMedida {
        return UnidadMedida(
            idUnidad = data.idUnidad,
            codigo = data.codigo,
            nombre = data.nombre,
            abreviatura = data.abreviatura,
            estado = data.estado
        )
    }

    fun toData(domain: UnidadMedida): UnidadMedidaData {
        return UnidadMedidaData(
            idUnidad = domain.idUnidad,
            codigo = domain.codigo,
            nombre = domain.nombre,
            abreviatura = domain.abreviatura,
            estado = domain.estado
        )
    }
}