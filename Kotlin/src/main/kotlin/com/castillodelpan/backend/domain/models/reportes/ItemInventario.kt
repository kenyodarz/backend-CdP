package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal

data class ItemInventario(
    val idProducto: Int,
    val nombreProducto: String,
    val categoria: String,
    val stockActual: Int,
    val precioUnitario: BigDecimal,
    val valorTotal: BigDecimal
)