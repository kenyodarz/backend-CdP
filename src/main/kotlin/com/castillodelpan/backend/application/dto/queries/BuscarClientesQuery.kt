package com.castillodelpan.backend.application.dto.queries

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Query para buscar clientes
 */
data class BuscarClientesQuery(
    val nombre: String?,
    val numeroDocumento: String?,
    val idRuta: Int?,
    val barrio: String?,
    val estado: EstadoGeneral?
)