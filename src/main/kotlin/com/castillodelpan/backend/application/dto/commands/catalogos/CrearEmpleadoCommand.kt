package com.castillodelpan.backend.application.dto.commands.catalogos

import java.time.LocalDate

data class CrearEmpleadoCommand(
    val numeroDocumento: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String?,
    val email: String?,
    val cargo: String?,
    val fechaIngreso: LocalDate?
)