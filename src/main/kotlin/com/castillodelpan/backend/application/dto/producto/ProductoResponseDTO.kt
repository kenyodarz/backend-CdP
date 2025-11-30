package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto
import java.math.BigDecimal

/** DTO de respuesta para producto Incluye precio base y todos los precios calculados */
data class ProductoResponseDTO(
    val idProducto: Int,
    val codigo: String?,
    val nombre: String,
    val descripcion: String?,
    val categoria: CategoriaSimpleDTO,
    val unidadMedida: UnidadMedidaSimpleDTO,
    val stockActual: Int,
    val stockMinimo: Int,
    val stockMaximo: Int?,
    val requiereLote: Boolean,
    val diasVidaUtil: Int?,
    val imagenUrl: String?,
    val estado: EstadoProducto,
    val stockBajo: Boolean,

    // Precio base del producto
    val precioBase: BigDecimal,

    // Todos los precios (calculados y especiales)
    val precios: Map<String, BigDecimal>
) {
    companion object {
        fun fromDomain(producto: Producto): ProductoResponseDTO {
            // Obtener todos los precios (calculados automáticamente + especiales)
            val todosLosPrecios =
                producto.getTodosLosPrecios().mapKeys {
                    it.key.name
                } // Convertir enum a string para JSON

            return ProductoResponseDTO(
                idProducto = producto.idProducto!!,
                codigo = producto.codigo,
                nombre = producto.nombre,
                descripcion = producto.descripcion,
                categoria =
                    CategoriaSimpleDTO(
                        idCategoria = producto.categoria.idCategoria!!,
                        nombre = producto.categoria.nombre
                    ),
                unidadMedida =
                    UnidadMedidaSimpleDTO(
                        idUnidad = producto.unidadMedida.idUnidad!!,
                        nombre = producto.unidadMedida.nombre,
                        abreviatura = producto.unidadMedida.abreviatura
                    ),
                stockActual = producto.stockActual,
                stockMinimo = producto.stockMinimo,
                stockMaximo = producto.stockMaximo,
                requiereLote = producto.requiereLote,
                diasVidaUtil = producto.diasVidaUtil,
                imagenUrl = producto.imagenUrl,
                estado = producto.estado,
                stockBajo = producto.stockBajo,
                precioBase = producto.precioBase,
                precios = todosLosPrecios
            )
        }
    }
}
