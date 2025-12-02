package com.castillodelpan.backend.application.usecases.inventario

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.domain.repositories.LoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Obtener Productos Vencidos
 */
@Service
@Transactional(readOnly = true)
class ObtenerProductosVencidosUseCase(
    private val loteRepository: LoteRepository
) {
    operator fun invoke(): List<Lote> {
        return loteRepository.findVencidos(LocalDate.now())
    }
}