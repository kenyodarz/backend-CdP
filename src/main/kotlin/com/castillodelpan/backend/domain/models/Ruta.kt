package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

data class Ruta(
    val idRuta: Int?,
    val nombre: String,
    val descripcion: String?,
    val estado: EstadoGeneral
)