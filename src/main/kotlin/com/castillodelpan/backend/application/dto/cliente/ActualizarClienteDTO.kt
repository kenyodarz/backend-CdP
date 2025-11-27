package com.castillodelpan.backend.application.dto.cliente

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * DTO para actualizar un cliente
 */
data class ActualizarClienteDTO(
    @field:NotBlank(message = "El nombre es obligatorio")
    val nombre: String,

    val codigo: String? = null,
    val tipoDocumento: TipoDocumento? = null,
    val numeroDocumento: String? = null,
    val direccion: String? = null,
    val telefono: String? = null,
    val barrio: String? = null,
    val comuna: String? = null,
    val tipoNegocio: String? = null,

    @field:NotNull(message = "El tipo de tarifa es obligatorio")
    val tipoTarifa: TipoTarifa,

    val idRuta: Int? = null,
    val idConductor: Int? = null,
    val horarioEntrega: String? = null,
    val estado: EstadoGeneral
)