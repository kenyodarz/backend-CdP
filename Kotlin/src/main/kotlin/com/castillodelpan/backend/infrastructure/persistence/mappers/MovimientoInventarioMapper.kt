package com.castillodelpan.backend.infrastructure.persistence.mappers

import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.MovimientoInventario

object MovimientoInventarioMapper {
    fun toDomain(data: MovimientoInventario): MovimientoInventarioDomain {
        return MovimientoInventarioDomain(
            idMovimiento = data.idMovimiento,
            producto = ProductoMapper.toDomain(data.producto),
            lote = data.lote?.let { LoteMapper.toDomain(it) },
            tipoMovimiento = data.tipoMovimiento,
            cantidad = data.cantidad,
            stockAnterior = data.stockAnterior,
            stockNuevo = data.stockNuevo,
            motivo = data.motivo,
            referencia = data.referencia,
            observaciones = data.observaciones,
            idUsuario = data.idUsuario,
            fechaMovimiento = data.fechaMovimiento
        )
    }
}