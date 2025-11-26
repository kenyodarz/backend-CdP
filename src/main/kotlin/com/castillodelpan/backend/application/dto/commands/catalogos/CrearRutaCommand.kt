package com.castillodelpan.backend.application.dto.commands.catalogos

data class CrearRutaCommand(
    val nombre: String,
    val descripcion: String?
)