package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Cambiar Estado de Orden
 */
@Service
@Transactional
class CambiarEstadoOrdenUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(idOrden: Int, nuevoEstado: EstadoOrden): OrdenDespacho {
        val orden = ordenRepository.findById(idOrden)
            ?: throw IllegalArgumentException("Orden no encontrada con ID: $idOrden")

        // Validar transición de estados
        validarTransicionEstado(orden.estado, nuevoEstado)

        val ordenActualizada = orden.copy(estado = nuevoEstado)
        return ordenRepository.save(ordenActualizada)
    }

    private fun validarTransicionEstado(estadoActual: EstadoOrden, nuevoEstado: EstadoOrden) {
        val transicionesValidas = mapOf(
            EstadoOrden.PENDIENTE to listOf(EstadoOrden.EN_PREPARACION, EstadoOrden.CANCELADA),
            EstadoOrden.EN_PREPARACION to listOf(EstadoOrden.LISTA, EstadoOrden.CANCELADA),
            EstadoOrden.LISTA to listOf(EstadoOrden.DESPACHADA, EstadoOrden.CANCELADA),
            EstadoOrden.DESPACHADA to listOf(EstadoOrden.ENTREGADA),
            EstadoOrden.ENTREGADA to emptyList(),
            EstadoOrden.CANCELADA to emptyList()
        )

        val estadosPermitidos = transicionesValidas[estadoActual] ?: emptyList()
        require(nuevoEstado in estadosPermitidos) {
            "No se puede cambiar de $estadoActual a $nuevoEstado"
        }
    }
}