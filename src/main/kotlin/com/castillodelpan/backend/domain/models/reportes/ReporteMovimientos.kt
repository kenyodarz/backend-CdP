package com.castillodelpan.backend.domain.models.reportes

import java.time.LocalDate

data class ReporteMovimientos(
    val fechaInicio: LocalDate,
    val fechaFin: LocalDate,
    val totalEntradas: Int,
    val totalSalidas: Int,
    val totalAjustes: Int,
    val numeroMovimientos: Int,
    val movimientosPorDia: List<MovimientosDia>
)