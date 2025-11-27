package com.castillodelpan.backend.application.dto.producto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * DTO para ajustar stock
 */
data class AjustarStockDTO(
    @field:NotNull(message = "La nueva cantidad es obligatoria")
    @field:Min(value = 0, message = "La cantidad no puede ser negativa")
    val nuevaCantidad: Int,

    @field:NotBlank(message = "El motivo es obligatorio")
    @field:Size(max = 200, message = "El motivo no puede exceder 200 caracteres")
    val motivo: String
)