package com.castillodelpan.backend.domain.models.producto

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import java.math.BigDecimal

/**
 * Modelo de dominio para Producto
 *
 * Usa un precio base y calcula automáticamente los precios con descuento. Permite precios
 * especiales opcionales para tarifas JM y CR.
 */
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

    // Precio base del producto (precio completo sin descuento)
    val precioBase: BigDecimal,

    // Precios especiales opcionales (solo para JM y CR si difieren del base)
    val preciosEspeciales: Map<TipoTarifa, BigDecimal> = emptyMap()
) {
    /** Verifica si hay stock disponible para la cantidad solicitada */
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

    /** Aumenta stock del producto */
    fun aumentarStock(cantidad: Int): Producto {
        require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
        return copy(stockActual = stockActual + cantidad)
    }

    /**
     * Obtiene el precio según el tipo de tarifa
     *
     * Si existe un precio especial para la tarifa, lo usa. Si no, calcula el precio automáticamente
     * aplicando el descuento.
     *
     * @param tarifa Tipo de tarifa del cliente
     * @return Precio calculado o especial para la tarifa
     */
    fun getPrecioPorTarifa(tarifa: TipoTarifa): BigDecimal {
        // Si tiene precio especial definido, usarlo
        preciosEspeciales[tarifa]?.let {
            return it
        }

        // Si no, calcular automáticamente desde el precio base
        return tarifa.calcularPrecio(precioBase)
    }

    /**
     * Obtiene todos los precios para todas las tarifas
     *
     * @return Mapa con todas las tarifas y sus precios correspondientes
     */
    fun getTodosLosPrecios(): Map<TipoTarifa, BigDecimal> {
        return TipoTarifa.values().associateWith { getPrecioPorTarifa(it) }
    }

    /** Verifica si el producto tiene stock bajo */
    val stockBajo: Boolean
        get() = stockActual <= stockMinimo
}
