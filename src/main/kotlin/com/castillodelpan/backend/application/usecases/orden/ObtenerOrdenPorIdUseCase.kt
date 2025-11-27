package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Orden por ID
 */
@Service
@Transactional(readOnly = true)
class ObtenerOrdenPorIdUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(idOrden: Int): OrdenDespacho {
        return ordenRepository.findById(idOrden)
            ?: throw IllegalArgumentException("Orden no encontrada con ID: $idOrden")
    }
}