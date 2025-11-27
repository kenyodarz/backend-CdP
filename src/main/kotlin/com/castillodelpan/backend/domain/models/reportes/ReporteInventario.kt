package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal
import java.time.LocalDate

data class ReporteInventario(
    val fecha: LocalDate,
    val valorTotalInventario: BigDecimal,
    val numeroProductos: Int,
    val productosStockBajo: Int,
    val items: List<ItemInventario>
)