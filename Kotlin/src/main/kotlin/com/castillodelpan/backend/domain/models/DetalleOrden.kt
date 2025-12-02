package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.producto.Producto
import java.math.BigDecimal

data class DetalleOrden(
    val idDetalle: Int?,
    val producto: Producto,
    val cantidad: Int,
    val precioUnitario: BigDecimal,
    val subtotal: BigDecimal
) {
    companion object {
        /**
         * Crea un detalle calculando el subtotal automáticamente
         */
        fun crear(
            producto: Producto,
            cantidad: Int,
            precioUnitario: BigDecimal
        ): DetalleOrden {
            require(cantidad > 0) { "La cantidad debe ser mayor a 0" }
            require(precioUnitario >= BigDecimal.ZERO) { "El precio no puede ser negativo" }

            val subtotal = precioUnitario.multiply(BigDecimal(cantidad))
            return DetalleOrden(
                idDetalle = null,
                producto = producto,
                cantidad = cantidad,
                precioUnitario = precioUnitario,
                subtotal = subtotal
            )
        }
    }
}