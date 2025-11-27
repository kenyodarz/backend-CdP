package com.castillodelpan.backend.application.dto.orden

import java.math.BigDecimal

/**
 * DTO para resumen de órdenes (dashboard)
 */
data class ResumenOrdenesDTO(
    val totalOrdenes: Int,
    val ordenesPendientes: Int,
    val ordenesEnPreparacion: Int,
    val ordenesListas: Int,
    val ordenesDespachadas: Int,
    val valorTotalOrdenes: BigDecimal
)