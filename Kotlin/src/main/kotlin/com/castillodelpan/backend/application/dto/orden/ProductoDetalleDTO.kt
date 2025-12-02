package com.castillodelpan.backend.application.dto.orden

data class ProductoDetalleDTO(
    val idProducto: Int,
    val codigo: String?,
    val nombre: String,
    val stockActual: Int
)