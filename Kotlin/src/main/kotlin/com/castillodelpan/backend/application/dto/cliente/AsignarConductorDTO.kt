package com.castillodelpan.backend.application.dto.cliente

import jakarta.validation.constraints.NotNull

/**
 * DTO para asignar conductor
 */
data class AsignarConductorDTO(
    @field:NotNull(message = "El ID del conductor es obligatorio")
    val idConductor: Int
)