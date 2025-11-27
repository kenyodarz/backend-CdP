package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal
import java.time.LocalDate

data class ReporteVentas(
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val totalVentas: BigDecimal,
    val numeroOrdenes: Int,
    val promedioVenta: BigDecimal,
    val ventasPorDia: List<VentaDiaria>
)