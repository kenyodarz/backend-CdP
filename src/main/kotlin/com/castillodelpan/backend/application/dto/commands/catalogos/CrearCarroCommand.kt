package com.castillodelpan.backend.application.dto.commands.catalogos

import java.math.BigDecimal

data class CrearCarroCommand(
    val placa: String,
    val modelo: String?,
    val capacidadKg: BigDecimal?
)