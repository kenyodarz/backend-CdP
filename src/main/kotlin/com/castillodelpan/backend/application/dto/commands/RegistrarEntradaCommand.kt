package com.castillodelpan.backend.application.dto.commands

import java.time.LocalDate

/**
 * Comando para registrar entrada de productos (desde producción)
 */
data class RegistrarEntradaCommand(
    val idProducto: Int,
    val cantidad: Int,
    val motivo: String,
    val referencia: String?,
    val observaciones: String?,
    val idUsuario: Int?,
    // Datos del lote (si el producto requiere lote)
    val codigoLote: String?,
    val fechaElaboracion: LocalDate?,
    val fechaVencimiento: LocalDate?
)