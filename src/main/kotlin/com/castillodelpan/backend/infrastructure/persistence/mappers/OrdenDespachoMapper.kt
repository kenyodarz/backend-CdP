package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ClienteData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.EmpleadoData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.OrdenDespachoData

object OrdenDespachoMapper {
    fun toDomain(data: OrdenDespachoData): OrdenDespacho {
        return OrdenDespacho(
            idOrden = data.idOrden,
            numeroOrden = data.numeroOrden,
            cliente = ClienteMapper.toDomain(data.cliente),
            empleado = EmpleadoMapper.toDomain(data.empleado),
            fechaOrden = data.fechaOrden,
            fechaEntregaProgramada = data.fechaEntregaProgramada,
            subtotal = data.subtotal,
            descuento = data.descuento,
            total = data.total,
            observaciones = data.observaciones,
            estado = data.estado,
            detalles = data.detalles.map { DetalleOrdenMapper.toDomain(it) }.toMutableList()
        )
    }

    fun toData(
        domain: OrdenDespacho,
        clienteData: ClienteData,
        empleadoData: EmpleadoData
    ): OrdenDespachoData {
        val ordenData = OrdenDespachoData(
            idOrden = domain.idOrden,
            numeroOrden = domain.numeroOrden,
            cliente = clienteData,
            empleado = empleadoData,
            fechaOrden = domain.fechaOrden,
            fechaEntregaProgramada = domain.fechaEntregaProgramada,
            subtotal = domain.subtotal,
            descuento = domain.descuento,
            total = domain.total,
            observaciones = domain.observaciones,
            estado = domain.estado
        )

        // Mapear detalles
        ordenData.detalles = domain.detalles.map {
            DetalleOrdenMapper.toData(it, ordenData)
        }.toMutableList()

        return ordenData
    }
}