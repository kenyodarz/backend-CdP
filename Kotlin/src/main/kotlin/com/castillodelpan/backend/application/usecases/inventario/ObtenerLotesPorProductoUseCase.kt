package com.castillodelpan.backend.application.usecases.inventario

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.domain.repositories.LoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Use Case: Obtener Lotes por Producto
 */
@Service
@Transactional(readOnly = true)
class ObtenerLotesPorProductoUseCase(
    private val loteRepository: LoteRepository
) {
    operator fun invoke(idProducto: Int): List<Lote> {
        return loteRepository.findByProductoIdActivos(idProducto)
    }
}