package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.domain.models.producto.Producto
import java.math.BigDecimal

/**
 * DTO simplificado para listados
 */
data class ProductoSimpleDTO(
    val idProducto: Int,
    val codigo: String?,
    val nombre: String,
    val stockActual: Int,
    val stockMinimo: Int,
    val stockBajo: Boolean,
    val precio0D: BigDecimal,
    val estado: EstadoProducto
) {
    companion object {
        fun fromDomain(producto: Producto): ProductoSimpleDTO {
            return ProductoSimpleDTO(
                idProducto = producto.idProducto!!,
                codigo = producto.codigo,
                nombre = producto.nombre,
                stockActual = producto.stockActual,
                stockMinimo = producto.stockMinimo,
                stockBajo = producto.stockBajo,
                precio0D = producto.getPrecioPorTarifa(TipoTarifa.PRECIO_0D),
                estado = producto.estado
            )
        }
    }
}