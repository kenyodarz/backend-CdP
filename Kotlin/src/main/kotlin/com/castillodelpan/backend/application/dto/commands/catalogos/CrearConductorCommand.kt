package com.castillodelpan.backend.application.dto.commands.catalogos

data class CrearConductorCommand(
    val numeroDocumento: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String?,
    val licencia: String?
)