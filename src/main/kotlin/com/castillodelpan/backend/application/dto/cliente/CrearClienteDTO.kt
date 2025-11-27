package com.castillodelpan.backend.application.dto.cliente

import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * DTO para crear un cliente
 */
data class CrearClienteDTO(
    @field:NotBlank(message = "El nombre es obligatorio")
    @field:Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    val nombre: String,

    @field:Size(max = 50, message = "El código no puede exceder 50 caracteres")
    val codigo: String? = null,

    val tipoDocumento: TipoDocumento? = null,

    @field:Size(max = 20, message = "El número de documento no puede exceder 20 caracteres")
    val numeroDocumento: String? = null,

    @field:Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    val direccion: String? = null,

    @field:Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    val telefono: String? = null,

    @field:Size(max = 100, message = "El barrio no puede exceder 100 caracteres")
    val barrio: String? = null,

    @field:Size(max = 50, message = "La comuna no puede exceder 50 caracteres")
    val comuna: String? = null,

    @field:Size(max = 100, message = "El tipo de negocio no puede exceder 100 caracteres")
    val tipoNegocio: String? = null,

    @field:NotNull(message = "El tipo de tarifa es obligatorio")
    val tipoTarifa: TipoTarifa,

    val idRuta: Int? = null,

    val idConductor: Int? = null,

    @field:Size(max = 50, message = "El horario no puede exceder 50 caracteres")
    val horarioEntrega: String? = null
)

