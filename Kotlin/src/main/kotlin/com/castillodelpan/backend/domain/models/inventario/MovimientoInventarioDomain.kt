package com.castillodelpan.backend.domain.models.inventario

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.domain.models.producto.Producto
import java.time.LocalDateTime

data class MovimientoInventarioDomain(
    val idMovimiento: Int?,
    val producto: Producto,
    val lote: Lote?,
    val tipoMovimiento: TipoMovimiento,
    val cantidad: Int,
    val stockAnterior: Int,
    val stockNuevo: Int,
    val motivo: String,
    val referencia: String?,
    val observaciones: String?,
    val idUsuario: Int?,
    val fechaMovimiento: LocalDateTime
) {
    companion object {
        /**
         * Crea un movimiento de entrada
         */
        fun crearEntrada(
            producto: Producto,
            cantidad: Int,
            motivo: String,
            lote: Lote? = null,
            referencia: String? = null,
            observaciones: String? = null,
            idUsuario: Int? = null
        ): MovimientoInventarioDomain {
            require(cantidad > 0) { "La cantidad debe ser mayor a 0" }

            val stockAnterior = producto.stockActual
            val stockNuevo = stockAnterior + cantidad

            return MovimientoInventarioDomain(
                idMovimiento = null,
                producto = producto,
                lote = lote,
                tipoMovimiento = TipoMovimiento.ENTRADA,
                cantidad = cantidad,
                stockAnterior = stockAnterior,
                stockNuevo = stockNuevo,
                motivo = motivo,
                referencia = referencia,
                observaciones = observaciones,
                idUsuario = idUsuario,
                fechaMovimiento = LocalDateTime.now()
            )
        }

        /**
         * Crea un movimiento de salida
         */
        fun crearSalida(
            producto: Producto,
            cantidad: Int,
            motivo: String,
            lote: Lote? = null,
            referencia: String? = null,
            observaciones: String? = null,
            idUsuario: Int? = null
        ): MovimientoInventarioDomain {
            require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
            require(producto.tieneStockDisponible(cantidad)) {
                "Stock insuficiente para la salida"
            }

            val stockAnterior = producto.stockActual
            val stockNuevo = stockAnterior - cantidad

            return MovimientoInventarioDomain(
                idMovimiento = null,
                producto = producto,
                lote = lote,
                tipoMovimiento = TipoMovimiento.SALIDA,
                cantidad = cantidad,
                stockAnterior = stockAnterior,
                stockNuevo = stockNuevo,
                motivo = motivo,
                referencia = referencia,
                observaciones = observaciones,
                idUsuario = idUsuario,
                fechaMovimiento = LocalDateTime.now()
            )
        }
    }
}