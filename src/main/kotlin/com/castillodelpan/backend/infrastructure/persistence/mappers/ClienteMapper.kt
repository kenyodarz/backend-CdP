package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ClienteData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ConductorData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.RutaData

object ClienteMapper {
    fun toDomain(data: ClienteData): Cliente {
        return Cliente(
            idCliente = data.idCliente,
            nombre = data.nombre,
            codigo = data.codigo,
            tipoDocumento = data.tipoDocumento,
            numeroDocumento = data.numeroDocumento,
            direccion = data.direccion,
            telefono = data.telefono,
            barrio = data.barrio,
            comuna = data.comuna,
            tipoNegocio = data.tipoNegocio,
            tipoTarifa = data.tipoTarifa,
            ruta = data.ruta?.let { RutaMapper.toDomain(it) },
            conductor = data.conductor?.let { ConductorMapper.toDomain(it) },
            horarioEntrega = data.horarioEntrega,
            estado = data.estado
        )
    }

    fun toData(
        domain: Cliente,
        rutaData: RutaData? = null,
        conductorData: ConductorData? = null
    ): ClienteData {
        return ClienteData(
            idCliente = domain.idCliente,
            nombre = domain.nombre,
            codigo = domain.codigo,
            tipoDocumento = domain.tipoDocumento,
            numeroDocumento = domain.numeroDocumento,
            direccion = domain.direccion,
            telefono = domain.telefono,
            barrio = domain.barrio,
            comuna = domain.comuna,
            tipoNegocio = domain.tipoNegocio,
            tipoTarifa = domain.tipoTarifa,
            ruta = rutaData,
            conductor = conductorData,
            horarioEntrega = domain.horarioEntrega,
            estado = domain.estado
        )
    }
}