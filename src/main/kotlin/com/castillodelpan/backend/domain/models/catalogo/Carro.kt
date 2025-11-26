package com.castillodelpan.backend.domain.models.catalogo

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import java.math.BigDecimal
import java.time.LocalDateTime

data class Carro(
    val idCarro: Int?,
    val placa: String,
    val modelo: String?,
    val capacidadKg: BigDecimal?,
    val estado: EstadoGeneral,
    val createdAt: LocalDateTime? = null
)