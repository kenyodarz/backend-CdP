package com.castillodelpan.backend.application.dto.orden

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * DTO para cancelar orden
 */
data class CancelarOrdenDTO(
    @field:NotNull(message = "El motivo es obligatorio")
    @field:Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
    val motivo: String
)