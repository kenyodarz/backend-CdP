package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.EstadoOrden

/**
 * Comando para actualizar estado de una orden
 */
data class ActualizarEstadoOrdenCommand(
    val idOrden: Int,
    val nuevoEstado: EstadoOrden,
    val observaciones: String?
)