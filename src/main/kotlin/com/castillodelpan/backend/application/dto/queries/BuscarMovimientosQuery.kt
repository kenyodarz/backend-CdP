package com.castillodelpan.backend.application.dto.queries

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import java.time.LocalDate

/**
 * Query para obtener movimientos de inventario
 */
data class BuscarMovimientosQuery(
    val idProducto: Int?,
    val tipoMovimiento: TipoMovimiento?,
    val fechaInicio: LocalDate?,
    val fechaFin: LocalDate?
)