package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.domain.models.inventario.MovimientoInventarioDomain
import com.castillodelpan.backend.domain.repositories.MovimientoInventarioRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.MovimientoInventarioMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaMovimientoInventarioRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional
class MovimientoInventarioRepositoryImpl(
    private val jpaMovimientoInventarioRepository: JpaMovimientoInventarioRepository
) : MovimientoInventarioRepository {

    override fun findById(id: Int): MovimientoInventarioDomain? {
        return jpaMovimientoInventarioRepository.findById(id)
            .map { MovimientoInventarioMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByProductoId(idProducto: Int): List<MovimientoInventarioDomain> {
        return jpaMovimientoInventarioRepository.findByProductoId(idProducto)
            .map { MovimientoInventarioMapper.toDomain(it) }
    }

    override fun findByFecha(fecha: LocalDate): List<MovimientoInventarioDomain> {
        return jpaMovimientoInventarioRepository.findByFecha(fecha)
            .map { MovimientoInventarioMapper.toDomain(it) }
    }

    override fun findByTipoAndFecha(
        tipo: TipoMovimiento,
        fecha: LocalDate
    ): List<MovimientoInventarioDomain> {
        return jpaMovimientoInventarioRepository.findByTipoAndFecha(tipo, fecha)
            .map { MovimientoInventarioMapper.toDomain(it) }
    }

    override fun save(movimiento: MovimientoInventarioDomain): MovimientoInventarioDomain {
        // Nota: Este mapper requiere que el producto ya tenga su Data equivalente
        // En la práctica, esto se maneja en el Use Case que coordina to-do
        throw NotImplementedError("Usar el servicio de inventario para crear movimientos")
    }
}