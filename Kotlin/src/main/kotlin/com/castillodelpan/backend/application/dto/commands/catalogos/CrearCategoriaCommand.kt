package com.castillodelpan.backend.application.dto.commands.catalogos

data class CrearCategoriaCommand(
    val nombre: String,
    val descripcion: String?
)