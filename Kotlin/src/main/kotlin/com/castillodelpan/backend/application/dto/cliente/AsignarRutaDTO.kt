package com.castillodelpan.backend.application.dto.cliente

import jakarta.validation.constraints.NotNull

/**
 * DTO para asignar ruta
 */
data class AsignarRutaDTO(
    @field:NotNull(message = "El ID de la ruta es obligatorio")
    val idRuta: Int
)