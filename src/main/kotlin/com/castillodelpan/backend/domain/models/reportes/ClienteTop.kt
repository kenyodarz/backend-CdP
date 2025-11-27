package com.castillodelpan.backend.domain.models.reportes

import java.math.BigDecimal

data class ClienteTop(
    val idCliente: Int,
    val nombreCliente: String,
    val totalCompras: BigDecimal,
    val numeroOrdenes: Int,
    val promedioCompra: BigDecimal
)