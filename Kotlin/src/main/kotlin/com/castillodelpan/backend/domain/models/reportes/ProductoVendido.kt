package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal

data class ProductoVendido(
    val idProducto: Int,
    val nombreProducto: String,
    val cantidadVendida: Int,
    val valorTotal: BigDecimal,
    val numeroOrdenes: Int
)