package com.castillodelpan.backend.application.dto.cliente

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoTarifa

/**
 * DTO simplificado para listados
 */
data class ClienteSimpleDTO(
    val idCliente: Int,
    val nombre: String,
    val codigo: String?,
    val telefono: String?,
    val barrio: String?,
    val tipoTarifa: TipoTarifa,
    val ruta: String?,
    val estado: EstadoGeneral
) {
    companion object {
        fun fromDomain(cliente: Cliente): ClienteSimpleDTO {
            return ClienteSimpleDTO(
                idCliente = cliente.idCliente!!,
                nombre = cliente.nombre,
                codigo = cliente.codigo,
                telefono = cliente.telefono,
                barrio = cliente.barrio,
                tipoTarifa = cliente.tipoTarifa,
                ruta = cliente.ruta?.nombre,
                estado = cliente.estado
            )
        }
    }
}