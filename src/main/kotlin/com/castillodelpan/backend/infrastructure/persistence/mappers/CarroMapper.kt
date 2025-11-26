package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.catalogo.Carro
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CarroData
import java.time.LocalDateTime

object CarroMapper {
    fun toDomain(data: CarroData): Carro {
        return Carro(
            idCarro = data.idCarro,
            placa = data.placa,
            modelo = data.modelo,
            capacidadKg = data.capacidadKg,
            estado = data.estado
        )
    }

    fun toData(domain: Carro): CarroData {
        return CarroData(
            idCarro = domain.idCarro,
            placa = domain.placa,
            modelo = domain.modelo,
            capacidadKg = domain.capacidadKg,
            estado = domain.estado,
            createdAt = domain.createdAt ?: LocalDateTime.now()
        )
    }
}