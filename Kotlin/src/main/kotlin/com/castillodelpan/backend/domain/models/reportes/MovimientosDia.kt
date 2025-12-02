package com.castillodelpan.backend.domain.models.reportes

import java.time.LocalDate

data class MovimientosDia(
    val fecha: LocalDate,
    val entradas: Int,
    val salidas: Int,
    val ajustes: Int
)