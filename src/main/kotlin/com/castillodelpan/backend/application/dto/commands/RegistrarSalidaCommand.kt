package com.castillodelpan.backend.application.dto.commands

/**
 * Comando para registrar salida de productos (despacho)
 */
data class RegistrarSalidaCommand(
    val idProducto: Int,
    val cantidad: Int,
    val motivo: String,
    val referencia: String?, // Ej: número de orden
    val observaciones: String?,
    val idUsuario: Int?,
    val idLote: Int? // Si se descuenta de un lote específico
)