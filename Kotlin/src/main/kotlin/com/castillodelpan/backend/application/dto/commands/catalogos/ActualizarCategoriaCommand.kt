package com.castillodelpan.backend.application.dto.commands.catalogos

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

data class ActualizarCategoriaCommand(
    val idCategoria: Int,
    val nombre: String,
    val descripcion: String?,
    val estado: EstadoGeneral
)