package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.MotivoDevolucion

/**
 * Comando para registrar devolución
 */
data class RegistrarDevolucionCommand(
    val idDespacho: Int,
    val idProducto: Int,
    val cantidad: Int,
    val motivo: MotivoDevolucion,
    val observaciones: String?,
    val idUsuario: Int?
)