package com.castillodelpan.backend.application.dto.orden

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

/**
 * DTO para un detalle de orden
 */
data class DetalleOrdenDTO(
    @field:NotNull(message = "El producto es obligatorio")
    val idProducto: Int,

    @field:NotNull(message = "La cantidad es obligatoria")
    @field:Min(value = 1, message = "La cantidad debe ser al menos 1")
    val cantidad: Int
)