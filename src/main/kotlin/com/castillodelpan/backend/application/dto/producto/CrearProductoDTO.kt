package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import com.castillodelpan.backend.domain.models.producto.Producto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

/**
 * DTO para crear un producto
 */
data class CrearProductoDTO(
    @field:Size(max = 50, message = "El código no puede exceder 50 caracteres")
    val codigo: String?,

    @field:NotBlank(message = "El nombre es obligatorio")
    @field:Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    val nombre: String,

    @field:Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    val descripcion: String?,

    @field:NotNull(message = "La categoría es obligatoria")
    @field:Positive(message = "ID de categoría debe ser positivo")
    val idCategoria: Int,

    @field:NotNull(message = "La unidad de medida es obligatoria")
    @field:Positive(message = "ID de unidad debe ser positivo")
    val idUnidad: Int,

    @field:Min(value = 0, message = "El stock actual no puede ser negativo")
    val stockActual: Int = 0,

    @field:Min(value = 0, message = "El stock mínimo no puede ser negativo")
    val stockMinimo: Int = 5,

    @field:Min(value = 0, message = "El stock máximo no puede ser negativo")
    val stockMaximo: Int? = null,

    val requiereLote: Boolean = false,

    @field:Min(value = 1, message = "Los días de vida útil deben ser al menos 1")
    val diasVidaUtil: Int? = null,

    @field:Size(max = 255, message = "La URL de imagen no puede exceder 255 caracteres")
    val imagenUrl: String? = null,

    @field:NotNull(message = "Los precios son obligatorios")
    @field:Size(min = 1, message = "Debe definir al menos un precio")
    val precios: List<PrecioDTO>
) {
    fun toDomain(categoria: Categoria, unidadMedida: UnidadMedida): Producto {
        return Producto(
            idProducto = null,
            codigo = codigo,
            nombre = nombre,
            descripcion = descripcion,
            categoria = categoria,
            unidadMedida = unidadMedida,
            stockActual = stockActual,
            stockMinimo = stockMinimo,
            stockMaximo = stockMaximo,
            requiereLote = requiereLote,
            diasVidaUtil = diasVidaUtil,
            imagenUrl = imagenUrl,
            estado = EstadoProducto.ACTIVO,
            precios = precios.map { it.toDomain() }.toMutableList()
        )
    }
}

