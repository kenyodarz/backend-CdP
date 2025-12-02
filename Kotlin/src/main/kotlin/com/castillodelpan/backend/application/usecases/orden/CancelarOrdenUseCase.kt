package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Cancelar Orden
 */
@Service
@Transactional
class CancelarOrdenUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(idOrden: Int, motivo: String): OrdenDespacho {
        require(motivo.isNotBlank()) { "Debe especificar un motivo de cancelación" }

        val orden = ordenRepository.findById(idOrden)
            ?: throw IllegalArgumentException("Orden no encontrada con ID: $idOrden")

        require(orden.estado in listOf(EstadoOrden.PENDIENTE, EstadoOrden.EN_PREPARACION)) {
            "Solo se pueden cancelar órdenes en estado PENDIENTE o EN_PREPARACION"
        }

        val ordenCancelada = orden.copy(
            estado = EstadoOrden.CANCELADA,
            observaciones = "${orden.observaciones ?: ""}\nMotivo cancelación: $motivo"
        )

        return ordenRepository.save(ordenCancelada)
    }
}