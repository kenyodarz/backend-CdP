package com.castillodelpan.backend.application.usecases.inventario

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.domain.repositories.LoteRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

/**
 * Use Case: Obtener Productos Próximos a Vencer
 */
@Service
@Transactional(readOnly = true)
class ObtenerProductosProximosAVencerUseCase(
    private val loteRepository: LoteRepository
) {
    operator fun invoke(diasAnticipacion: Int = 3): List<Lote> {
        val hoy = LocalDate.now()
        val fechaLimite = hoy.plusDays(diasAnticipacion.toLong())
        return loteRepository.findProximosAVencer(hoy, fechaLimite)
    }
}