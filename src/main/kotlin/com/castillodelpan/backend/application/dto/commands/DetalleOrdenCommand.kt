package com.castillodelpan.backend.application.dto.commands

import java.math.BigDecimal

data class DetalleOrdenCommand(
    val idProducto: Int,
    val cantidad: Int,
    val precioUnitario: BigDecimal? = null // Si es null, se calcula según tarifa del cliente
)