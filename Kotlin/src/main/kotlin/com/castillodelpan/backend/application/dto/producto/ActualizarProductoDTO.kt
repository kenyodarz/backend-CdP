package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.enums.EstadoProducto
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.math.BigDecimal

/** DTO para actualizar un producto */
data class ActualizarProductoDTO(
    @field:Size(max = 50, message = "El código no puede exceder 50 caracteres")
    val codigo: String?,
    @field:NotBlank(message = "El nombre es obligatorio")
    @field:Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    val nombre: String,
    val descripcion: String?,
    @field:NotNull(message = "La categoría es obligatoria") val idCategoria: Int,
    @field:NotNull(message = "La unidad de medida es obligatoria") val idUnidad: Int,
    @field:Min(value = 0, message = "El stock mínimo no puede ser negativo")
    val stockMinimo: Int,
    val stockMaximo: Int? = null,
    val requiereLote: Boolean,
    val diasVidaUtil: Int? = null,
    val imagenUrl: String? = null,
    val estado: EstadoProducto,

    // Precio base del producto
    @field:NotNull(message = "El precio base es obligatorio")
    @field:Positive(message = "El precio base debe ser mayor a 0")
    val precioBase: BigDecimal,

    // Precios especiales opcionales
    @field:PositiveOrZero(message = "El precio JM no puede ser negativo")
    val precioJM: BigDecimal? = null,
    @field:PositiveOrZero(message = "El precio CR no puede ser negativo")
    val precioCR: BigDecimal? = null
)
