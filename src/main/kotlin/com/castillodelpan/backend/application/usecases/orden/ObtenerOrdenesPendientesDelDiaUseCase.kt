package com.castillodelpan.backend.application.usecases.orden

import com.castillodelpan.backend.domain.models.OrdenDespacho
import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.domain.repositories.OrdenDespachoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Obtener Órdenes Pendientes del Día
 */
@Service
@Transactional(readOnly = true)
class ObtenerOrdenesPendientesDelDiaUseCase(
    private val ordenRepository: OrdenDespachoRepository
) {
    operator fun invoke(): List<OrdenDespacho> {
        val hoy = LocalDate.now()
        return ordenRepository.findByFechaAndEstado(hoy, EstadoOrden.PENDIENTE) +
                ordenRepository.findByFechaAndEstado(hoy, EstadoOrden.EN_PREPARACION)
    }
}