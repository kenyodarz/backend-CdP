package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

data class UnidadMedida(
    val idUnidad: Int?,
    val codigo: String,
    val nombre: String,
    val abreviatura: String,
    val estado: EstadoGeneral
)