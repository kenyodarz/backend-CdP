package com.castillodelpan.backend.application.dto.commands

/**
 * Comando para ajustar stock manualmente
 */
data class AjustarStockCommand(
    val idProducto: Int,
    val cantidad: Int,
    val motivo: String,
    val observaciones: String?,
    val idUsuario: Int?
)