package com.castillodelpan.backend.application.dto.orden

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import jakarta.validation.constraints.NotNull

/**
 * DTO para cambiar estado de orden
 */
data class CambiarEstadoOrdenDTO(
    @field:NotNull(message = "El nuevo estado es obligatorio")
    val nuevoEstado: EstadoOrden
)