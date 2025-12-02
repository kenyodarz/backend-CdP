package com.castillodelpan.backend.application.usecases.movimientos

import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import com.castillodelpan.backend.domain.repositories.MovimientoInventarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Obtener Movimientos por Fecha
 */
@Service
@Transactional(readOnly = true)
class ObtenerMovimientosPorFechaUseCase(
    private val movimientoRepository: MovimientoInventarioRepository
) {
    operator fun invoke(fecha: LocalDate): List<MovimientoInventarioDomain> {
        return movimientoRepository.findByFecha(fecha)
    }
}