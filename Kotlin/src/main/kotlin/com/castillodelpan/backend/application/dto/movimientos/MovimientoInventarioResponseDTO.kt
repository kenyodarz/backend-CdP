package com.castillodelpan.backend.application.dto.movimientos

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import java.time.LocalDateTime

data class MovimientoInventarioResponseDTO(
    val idMovimiento: Int,
    val productoNombre: String,
    val loteCodigoLote: String?,
    val tipoMovimiento: TipoMovimiento,
    val cantidad: Int,
    val stockAnterior: Int,
    val stockNuevo: Int,
    val motivo: String,
    val referencia: String?,
    val observaciones: String?,
    val fechaMovimiento: LocalDateTime
) {
    companion object {
        fun fromDomain(movimiento: MovimientoInventarioDomain): MovimientoInventarioResponseDTO {
            return MovimientoInventarioResponseDTO(
                idMovimiento = movimiento.idMovimiento!!,
                productoNombre = movimiento.producto.nombre,
                loteCodigoLote = movimiento.lote?.codigoLote,
                tipoMovimiento = movimiento.tipoMovimiento,
                cantidad = movimiento.cantidad,
                stockAnterior = movimiento.stockAnterior,
                stockNuevo = movimiento.stockNuevo,
                motivo = movimiento.motivo,
                referencia = movimiento.referencia,
                observaciones = movimiento.observaciones,
                fechaMovimiento = movimiento.fechaMovimiento
            )
        }
    }
}