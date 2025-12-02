package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.EstadoProducto

/**
 * Comando para actualizar un producto
 */
data class ActualizarProductoCommand(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String?,
    val idCategoria: Int,
    val idUnidad: Int,
    val stockMinimo: Int,
    val stockMaximo: Int?,
    val diasVidaUtil: Int?,
    val estado: EstadoProducto,
    val precios: List<PrecioProductoCommand>
)