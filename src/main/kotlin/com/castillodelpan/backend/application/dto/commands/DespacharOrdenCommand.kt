package com.castillodelpan.backend.application.dto.commands

/**
 * Comando para despachar una orden en un carro
 */
data class DespacharOrdenCommand(
    val idOrden: Int,
    val idCarro: Int,
    val idConductor: Int,
    val observaciones: String?,
    val idUsuario: Int?
)