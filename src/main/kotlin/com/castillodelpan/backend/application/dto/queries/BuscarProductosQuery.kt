package com.castillodelpan.backend.application.dto.queries

import com.castillodelpan.backend.domain.models.enums.EstadoProducto

/**
 * Query para buscar productos
 */
data class BuscarProductosQuery(
    val nombre: String?,
    val idCategoria: Int?,
    val estado: EstadoProducto?,
    val soloStockBajo: Boolean = false
)