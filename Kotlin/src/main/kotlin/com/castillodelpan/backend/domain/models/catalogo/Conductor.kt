package com.castillodelpan.backend.domain.models.catalogo

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import java.time.LocalDateTime

data class Conductor(
    val idConductor: Int?,
    val numeroDocumento: String,
    val nombres: String,
    val apellidos: String,
    val telefono: String?,
    val licencia: String?,
    val estado: EstadoGeneral,
    val createdAt: LocalDateTime? = null
) {
    val nombreCompleto: String
        get() = "$nombres $apellidos"
}