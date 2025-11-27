package com.castillodelpan.backend.application.dto.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import java.math.BigDecimal
import java.time.LocalDate

/**
 * DTO simplificado para listados
 */
data class OrdenDespachoSimpleDTO(
    val idOrden: Int,
    val numeroOrden: String,
    val clienteNombre: String,
    val fechaOrden: LocalDate,
    val total: BigDecimal,
    val estado: EstadoOrden,
    val cantidadProductos: Int
) {
    companion object {
        fun fromDomain(orden: OrdenDespacho): OrdenDespachoSimpleDTO {
            return OrdenDespachoSimpleDTO(
                idOrden = orden.idOrden!!,
                numeroOrden = orden.numeroOrden,
                clienteNombre = orden.cliente.nombre,
                fechaOrden = orden.fechaOrden,
                total = orden.total,
                estado = orden.estado,
                cantidadProductos = orden.detalles.size
            )
        }
    }
}