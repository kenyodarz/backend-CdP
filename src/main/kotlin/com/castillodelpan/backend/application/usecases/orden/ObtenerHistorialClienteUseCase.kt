package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Historial de Cliente
 */
@Service
@Transactional(readOnly = true)
class ObtenerHistorialClienteUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(idCliente: Int, limite: Int = 20): List<OrdenDespacho> {
        return ordenRepository.findByCliente(idCliente).take(limite)
    }
}