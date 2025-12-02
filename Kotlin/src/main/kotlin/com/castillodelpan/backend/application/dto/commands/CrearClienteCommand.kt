package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa

/**
 * Comando para crear un cliente
 */
data class CrearClienteCommand(
    val nombre: String,
    val codigo: String?,
    val tipoDocumento: TipoDocumento?,
    val numeroDocumento: String?,
    val direccion: String?,
    val telefono: String?,
    val barrio: String?,
    val comuna: String?,
    val tipoNegocio: String?,
    val tipoTarifa: TipoTarifa,
    val idRuta: Int?,
    val idConductor: Int?,
    val horarioEntrega: String?
)