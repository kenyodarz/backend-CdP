package com.castillodelpan.backend.application.dto.commands

import java.time.LocalDate

/**
 * Comando para crear una orden de despacho
 */
data class CrearOrdenCommand(
    val idCliente: Int,
    val idEmpleado: Int,
    val fechaEntregaProgramada: LocalDate?,
    val observaciones: String?,
    val detalles: List<DetalleOrdenCommand>
)