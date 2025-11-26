package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa

/**
 * Comando para actualizar un cliente
 */
data class ActualizarClienteCommand(
    val idCliente: Int,
    val nombre: String,
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
    val horarioEntrega: String?,
    val estado: EstadoGeneral
)