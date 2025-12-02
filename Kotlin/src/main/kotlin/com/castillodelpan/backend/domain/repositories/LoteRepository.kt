package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.inventario.Lote
import java.time.LocalDate

/**
 * Repositorio de Lotes
 */
interface LoteRepository {
    fun findById(id: Int): Lote?
    fun findByCodigoLote(codigoLote: String): Lote?
    fun findByProductoIdActivos(idProducto: Int): List<Lote>
    fun findProximosAVencer(fechaInicio: LocalDate, fechaFin: LocalDate): List<Lote>
    fun findVencidos(fecha: LocalDate): List<Lote>
    fun save(lote: Lote): Lote
    fun delete(id: Int)
}