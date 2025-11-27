package com.castillodelpan.backend.application.dto.inventario

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class CrearLoteDTO(
    @field:NotNull(message = "El producto es obligatorio")
    val idProducto: Int,

    @field:NotBlank(message = "El código de lote es obligatorio")
    @field:Size(max = 50, message = "El código no puede exceder 50 caracteres")
    val codigoLote: String,

    @field:NotNull(message = "La fecha de elaboración es obligatoria")
    val fechaElaboracion: LocalDate,

    val fechaVencimiento: LocalDate? = null,

    @field:NotNull(message = "La cantidad es obligatoria")
    @field:Min(value = 1, message = "La cantidad debe ser al menos 1")
    val cantidad: Int
)
