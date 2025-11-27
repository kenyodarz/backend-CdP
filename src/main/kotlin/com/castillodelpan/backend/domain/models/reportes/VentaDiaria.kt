package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal
import java.time.LocalDate

data class VentaDiaria(
    val fecha: LocalDate,
    val numeroOrdenes: Int,
    val totalVentas: BigDecimal
)