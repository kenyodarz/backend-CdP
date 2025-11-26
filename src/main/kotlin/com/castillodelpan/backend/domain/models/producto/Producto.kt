package com.castillodelpan.backend.domain.models.producto

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import java.math.BigDecimal

data class Producto(
    val idProducto: Int?,
    val codigo: String?,
    val nombre: String,
    val descripcion: String?,
    val categoria: Categoria,
    val unidadMedida: UnidadMedida,
    var stockActual: Int,
    val stockMinimo: Int,
    val stockMaximo: Int?,
    val requiereLote: Boolean,
    val diasVidaUtil: Int?,
    val imagenUrl: String?,
    val estado: EstadoProducto,
    val precios: MutableList<PrecioProducto> = mutableListOf()
) {
    /**
     * Verifica si hay stock disponible para la cantidad solicitada
     */
    fun tieneStockDisponible(cantidad: Int): Boolean {
        return stockActual >= cantidad
    }

    /**
     * Descuenta stock del producto
     * @throws IllegalArgumentException si la cantidad es mayor al stock
     */
    fun descontarStock(cantidad: Int): Producto {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
        require(tieneStockDisponible(cantidad)) {
            "Stock insuficiente. Disponible: $stockActual, Solicitado: $cantidad"
        }
        return copy(stockActual = stockActual - cantidad)
    }

    /**
     * Aumenta stock del producto
     */
    fun aumentarStock(cantidad: Int): Producto {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
        return copy(stockActual = stockActual + cantidad)
    }

    /**
     * Obtiene el precio según el tipo de tarifa
     */
    fun getPrecioPorTarifa(tarifa: TipoTarifa): BigDecimal {
        return precios.firstOrNull { it.tipoTarifa == tarifa }?.precio ?: BigDecimal.ZERO
    }

    /**
     * Verifica si el producto tiene stock bajo
     */
    val stockBajo: Boolean
        get() = stockActual <= stockMinimo
}

