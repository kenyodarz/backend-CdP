package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.DetalleOrden
import com.castillodelpan.backend.infrastructure.persistence.entities.core.DetalleOrdenData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.OrdenDespachoData

object DetalleOrdenMapper {
    fun toDomain(data: DetalleOrdenData): DetalleOrden {
        return DetalleOrden(
            idDetalle = data.idDetalle,
            producto = ProductoMapper.toDomain(data.producto),
            cantidad = data.cantidad,
            precioUnitario = data.precioUnitario,
            subtotal = data.subtotal
        )
    }

    fun toData(domain: DetalleOrden, ordenData: OrdenDespachoData): DetalleOrdenData {
        // Nota: productoData debe venir de un repositorio en la implementación real
        return DetalleOrdenData(
            idDetalle = domain.idDetalle,
            orden = ordenData,
            producto = ProductoMapper.toData(
                domain.producto,
                CategoriaMapper.toData(domain.producto.categoria),
                UnidadMedidaMapper.toData(domain.producto.unidadMedida)
            ),
            cantidad = domain.cantidad,
            precioUnitario = domain.precioUnitario,
            subtotal = domain.subtotal
        )
    }
}