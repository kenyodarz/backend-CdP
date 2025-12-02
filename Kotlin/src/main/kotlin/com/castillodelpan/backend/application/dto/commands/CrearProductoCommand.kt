package com.castillodelpan.backend.application.dto.commands

/**
 * Comando para crear un producto
 */
data class CrearProductoCommand(
    val codigo: String?,
    val nombre: String,
    val descripcion: String?,
    val idCategoria: Int,
    val idUnidad: Int,
    val stockInicial: Int,
    val stockMinimo: Int,
    val stockMaximo: Int?,
    val requiereLote: Boolean,
    val diasVidaUtil: Int?,
    val precios: List<PrecioProductoCommand>
)