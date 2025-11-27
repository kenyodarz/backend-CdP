package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Listar Órdenes por Estado
 */
@Service
@Transactional(readOnly = true)
class ListarOrdenesPorEstadoUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(estado: EstadoOrden): List<OrdenDespacho> {
        return ordenRepository.findByEstado(estado)
    }
}