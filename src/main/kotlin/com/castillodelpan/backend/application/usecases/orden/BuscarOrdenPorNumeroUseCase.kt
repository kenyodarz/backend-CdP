package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Buscar Orden por Número
 */
@Service
@Transactional(readOnly = true)
class BuscarOrdenPorNumeroUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(numeroOrden: String): OrdenDespacho {
        return ordenRepository.findByNumeroOrden(numeroOrden)
            ?: throw IllegalArgumentException("Orden no encontrada con número: $numeroOrden")
    }
}