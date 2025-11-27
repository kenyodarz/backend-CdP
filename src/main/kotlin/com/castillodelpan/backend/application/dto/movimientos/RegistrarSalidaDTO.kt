package com.castillodelpan.backend.application.dto.movimientos

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegistrarSalidaDTO(
    @field:NotNull(message = "El producto es obligatorio")
    val idProducto: Int,

    @field:NotNull(message = "La cantidad es obligatoria")
    @field:Min(value = 1, message = "La cantidad debe ser al menos 1")
    val cantidad: Int,

    @field:NotBlank(message = "El motivo es obligatorio")
    @field:Size(max = 200, message = "El motivo no puede exceder 200 caracteres")
    val motivo: String,

    @field:Size(max = 50, message = "El código de lote no puede exceder 50 caracteres")
    val codigoLote: String? = null,

    @field:Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    val referencia: String? = null,

    @field:Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    val observaciones: String? = null
)