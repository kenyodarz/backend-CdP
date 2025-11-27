package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Listar Órdenes por Fecha
 */
@Service
@Transactional(readOnly = true)
class ListarOrdenesPorFechaUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(fecha: LocalDate): List<OrdenDespacho> {
        return ordenRepository.findByFechaOrden(fecha)
    }
}