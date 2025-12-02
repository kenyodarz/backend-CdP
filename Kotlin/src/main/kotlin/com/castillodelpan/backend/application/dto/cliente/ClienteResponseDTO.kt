package com.castillodelpan.backend.application.dto.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa

/**
 * DTO de respuesta para cliente
 */
data class ClienteResponseDTO(
    val idCliente: Int,
    val nombre: String,
    val codigo: String?,
    val tipoDocumento: TipoDocumento?,
    val numeroDocumento: String?,
    val direccion: String?,
    val telefono: String?,
    val barrio: String?,
    val comuna: String?,
    val tipoNegocio: String?,
    val tipoTarifa: TipoTarifa,
    val ruta: RutaSimpleDTO?,
    val conductor: ConductorSimpleDTO?,
    val horarioEntrega: String?,
    val estado: EstadoGeneral
) {
    companion object {
        fun fromDomain(cliente: Cliente): ClienteResponseDTO {
            return ClienteResponseDTO(
                idCliente = cliente.idCliente!!,
                nombre = cliente.nombre,
                codigo = cliente.codigo,
                tipoDocumento = cliente.tipoDocumento,
                numeroDocumento = cliente.numeroDocumento,
                direccion = cliente.direccion,
                telefono = cliente.telefono,
                barrio = cliente.barrio,
                comuna = cliente.comuna,
                tipoNegocio = cliente.tipoNegocio,
                tipoTarifa = cliente.tipoTarifa,
                ruta = cliente.ruta?.let {
                    RutaSimpleDTO(it.idRuta!!, it.nombre)
                },
                conductor = cliente.conductor?.let {
                    ConductorSimpleDTO(it.idConductor!!, it.nombreCompleto)
                },
                horarioEntrega = cliente.horarioEntrega,
                estado = cliente.estado
            )
        }
    }
}