package com.castillodelpan.backend.application.dto.orden

/**
 * DTOs auxiliares
 */
data class ClienteOrdenDTO(
    val idCliente: Int,
    val nombre: String,
    val direccion: String?,
    val telefono: String?,
    val barrio: String?
)