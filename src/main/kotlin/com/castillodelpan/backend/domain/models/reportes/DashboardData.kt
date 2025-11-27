package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal
import java.time.LocalDate

data class DashboardData(
    val fecha: LocalDate,
    val ordenesPendientes: Int,
    val ordenesEnPreparacion: Int,
    val ordenesListas: Int,
    val ordenesDespachadas: Int,
    val ventasHoy: BigDecimal,
    val productosStockBajo: Int,
    val totalProductos: Int,
    val clientesActivos: Int
)