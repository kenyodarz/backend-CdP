package com.castillodelpan.backend.application.dto.commands

/**
 * Comando para cancelar una orden
 */
data class CancelarOrdenCommand(
    val idOrden: Int,
    val motivo: String
)