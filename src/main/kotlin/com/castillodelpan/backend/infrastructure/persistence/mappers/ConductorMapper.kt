package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.catalogo.Conductor
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ConductorData
import java.time.LocalDateTime

object ConductorMapper {
    fun toDomain(data: ConductorData): Conductor {
        return Conductor(
            idConductor = data.idConductor,
            numeroDocumento = data.numeroDocumento,
            nombres = data.nombres,
            apellidos = data.apellidos,
            telefono = data.telefono,
            licencia = data.licencia,
            estado = data.estado
        )
    }

    fun toData(domain: Conductor): ConductorData {
        return ConductorData(
            idConductor = domain.idConductor,
            numeroDocumento = domain.numeroDocumento,
            nombres = domain.nombres,
            apellidos = domain.apellidos,
            telefono = domain.telefono,
            licencia = domain.licencia,
            estado = domain.estado,
            createdAt = domain.createdAt ?: LocalDateTime.now()
        )
    }
}