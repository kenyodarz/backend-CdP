package com.castillodelpan.backend.application.dto.queries

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import java.time.LocalDate

/**
 * Query para buscar órdenes
 */
data class BuscarOrdenesQuery(
    val fechaInicio: LocalDate?,
    val fechaFin: LocalDate?,
    val idCliente: Int?,
    val estado: EstadoOrden?
)