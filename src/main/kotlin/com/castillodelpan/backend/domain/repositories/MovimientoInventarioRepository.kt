package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import java.time.LocalDate

/**
 * Repositorio de Movimientos de Inventario
 */
interface MovimientoInventarioRepository {
    fun findById(id: Int): MovimientoInventarioDomain?
    fun findByProductoId(idProducto: Int): List<MovimientoInventarioDomain>
    fun findByFecha(fecha: LocalDate): List<MovimientoInventarioDomain>
    fun findByTipoAndFecha(tipo: TipoMovimiento, fecha: LocalDate): List<MovimientoInventarioDomain>
    fun save(movimiento: MovimientoInventarioDomain): MovimientoInventarioDomain
}