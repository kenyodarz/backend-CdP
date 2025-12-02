package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import java.math.BigDecimal
import java.time.LocalDate

data class OrdenDespacho(
    val idOrden: Int?,
    val numeroOrden: String,
    val cliente: Cliente,
    val empleado: Empleado,
    val fechaOrden: LocalDate,
    val fechaEntregaProgramada: LocalDate?,
    var subtotal: BigDecimal,
    var descuento: BigDecimal,
    var total: BigDecimal,
    val observaciones: String?,
    val estado: EstadoOrden,
    val detalles: MutableList<DetalleOrden> = mutableListOf()
) {
    /**
     * Calcula el total de la orden basado en los detalles
     */
    fun calcularTotal(): OrdenDespacho {
        val nuevoSubtotal = detalles.sumOf { it.subtotal }
        val nuevoTotal = nuevoSubtotal - descuento
        return copy(
            subtotal = nuevoSubtotal,
            total = nuevoTotal
        )
    }

    /**
     * Agrega un detalle a la orden
     */
    fun agregarDetalle(detalle: DetalleOrden): OrdenDespacho {
        val nuevosDetalles = detalles.toMutableList()
        nuevosDetalles.add(detalle)
        return copy(detalles = nuevosDetalles).calcularTotal()
    }

    /**
     * Valida si la orden puede ser despachada
     */
    fun puedeSerDespachada(): Boolean {
        return estado == EstadoOrden.LISTA && detalles.isNotEmpty()
    }

    /**
     * Valida si todos los productos tienen stock
     */
    fun todosProductosTienenStock(): Boolean {
        return detalles.all {
            it.producto.tieneStockDisponible(it.cantidad)
        }
    }
}