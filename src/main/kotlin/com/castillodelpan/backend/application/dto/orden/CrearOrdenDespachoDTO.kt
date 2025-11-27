package com.castillodelpan.backend.application.dto.orden

import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.LocalDate

/**
 * DTO para crear una orden de despacho
 */
data class CrearOrdenDespachoDTO(
    @field:NotNull(message = "El cliente es obligatorio")
    val idCliente: Int,

    @field:NotNull(message = "El empleado es obligatorio")
    val idEmpleado: Int,

    val fechaEntregaProgramada: LocalDate? = null,

    @field:Min(value = 0, message = "El descuento no puede ser negativo")
    val descuento: BigDecimal = BigDecimal.ZERO,

    @field:Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    val observaciones: String? = null,

    @field:NotEmpty(message = "Debe incluir al menos un producto")
    @field:Valid
    val detalles: List<DetalleOrdenDTO>
)

