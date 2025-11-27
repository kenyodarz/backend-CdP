package com.castillodelpan.backend.application.dto.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import java.math.BigDecimal
import java.time.LocalDate

/**
 * DTO de respuesta para orden de despacho
 */
data class OrdenDespachoResponseDTO(
    val idOrden: Int,
    val numeroOrden: String,
    val cliente: ClienteOrdenDTO,
    val empleado: EmpleadoOrdenDTO,
    val fechaOrden: LocalDate,
    val fechaEntregaProgramada: LocalDate?,
    val subtotal: BigDecimal,
    val descuento: BigDecimal,
    val total: BigDecimal,
    val observaciones: String?,
    val estado: EstadoOrden,
    val detalles: List<DetalleOrdenResponseDTO>,
    val cantidadProductos: Int,
    val puedeSerDespachada: Boolean
) {
    companion object {
        fun fromDomain(orden: OrdenDespacho): OrdenDespachoResponseDTO {
            return OrdenDespachoResponseDTO(
                idOrden = orden.idOrden!!,
                numeroOrden = orden.numeroOrden,
                cliente = ClienteOrdenDTO(
                    idCliente = orden.cliente.idCliente!!,
                    nombre = orden.cliente.nombre,
                    direccion = orden.cliente.direccion,
                    telefono = orden.cliente.telefono,
                    barrio = orden.cliente.barrio
                ),
                empleado = EmpleadoOrdenDTO(
                    idEmpleado = orden.empleado.idEmpleado!!,
                    nombreCompleto = orden.empleado.nombreCompleto
                ),
                fechaOrden = orden.fechaOrden,
                fechaEntregaProgramada = orden.fechaEntregaProgramada,
                subtotal = orden.subtotal,
                descuento = orden.descuento,
                total = orden.total,
                observaciones = orden.observaciones,
                estado = orden.estado,
                detalles = orden.detalles.map { DetalleOrdenResponseDTO.fromDomain(it) },
                cantidadProductos = orden.detalles.size,
                puedeSerDespachada = orden.puedeSerDespachada()
            )
        }
    }
}