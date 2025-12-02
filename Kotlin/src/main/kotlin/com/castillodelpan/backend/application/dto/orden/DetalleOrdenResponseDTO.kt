package com.castillodelpan.backend.application.dto.orden

import com.castillodelpan.backend.domain.models.DetalleOrden
import java.math.BigDecimal

/**
 * DTO para detalle de orden en respuesta
 */
data class DetalleOrdenResponseDTO(
    val idDetalle: Int,
    val producto: ProductoDetalleDTO,
    val cantidad: Int,
    val precioUnitario: BigDecimal,
    val subtotal: BigDecimal
) {
    companion object {
        fun fromDomain(detalle: DetalleOrden): DetalleOrdenResponseDTO {
            return DetalleOrdenResponseDTO(
                idDetalle = detalle.idDetalle!!,
                producto = ProductoDetalleDTO(
                    idProducto = detalle.producto.idProducto!!,
                    codigo = detalle.producto.codigo,
                    nombre = detalle.producto.nombre,
                    stockActual = detalle.producto.stockActual
                ),
                cantidad = detalle.cantidad,
                precioUnitario = detalle.precioUnitario,
                subtotal = detalle.subtotal
            )
        }
    }
}