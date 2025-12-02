package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import java.time.LocalDate
import java.time.LocalDateTime

data class Empleado(
    val idEmpleado: Int?,
    val numeroDocumento: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String?,
    val email: String?,
    val cargo: String?,
    val fechaIngreso: LocalDate?,
    val estado: EstadoGeneral,
    val createdAt: LocalDateTime? = null
) {
    val nombreCompleto: String
        get() = "$nombres $apellidos"
}