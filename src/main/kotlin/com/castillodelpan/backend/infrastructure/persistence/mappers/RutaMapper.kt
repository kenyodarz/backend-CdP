package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.Ruta
import com.castillodelpan.backend.infrastructure.persistence.entities.core.RutaData

object RutaMapper {
    fun toDomain(data: RutaData): Ruta {
        return Ruta(
            idRuta = data.idRuta,
            nombre = data.nombre,
            descripcion = data.descripcion,
            estado = data.estado
        )
    }

    fun toData(domain: Ruta): RutaData {
        return RutaData(
            idRuta = domain.idRuta,
            nombre = domain.nombre,
            descripcion = domain.descripcion,
            estado = domain.estado
        )
    }
}