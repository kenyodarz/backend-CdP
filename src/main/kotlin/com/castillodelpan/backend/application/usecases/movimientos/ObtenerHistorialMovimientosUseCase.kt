package com.castillodelpan.backend.application.usecases.movimientos

import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import com.castillodelpan.backend.domain.repositories.MovimientoInventarioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Historial de Movimientos de Producto
 */
@Service
@Transactional(readOnly = true)
class ObtenerHistorialMovimientosUseCase(
    private val movimientoRepository: MovimientoInventarioRepository
) {
    operator fun invoke(idProducto: Int): List<MovimientoInventarioDomain> {
        return movimientoRepository.findByProductoId(idProducto)
    }
}