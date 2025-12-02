package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

data class Categoria(
    val idCategoria: Int?,
    val nombre: String,
    val descripcion: String?,
    val estado: EstadoGeneral
)