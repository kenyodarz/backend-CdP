package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.inventario.Lote
import com.castillodelpan.backend.domain.repositories.LoteRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.LoteMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaLoteRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Repository
@Transactional
class LoteRepositoryImpl(
    private val jpaLoteRepository: JpaLoteRepository
) : LoteRepository {

    override fun findById(id: Int): Lote? {
        return jpaLoteRepository.findById(id)
            .map { LoteMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCodigoLote(codigoLote: String): Lote? {
        return jpaLoteRepository.findByCodigoLote(codigoLote)
            .map { LoteMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByProductoIdActivos(idProducto: Int): List<Lote> {
        return jpaLoteRepository.findByProductoIdActivos(idProducto)
            .map { LoteMapper.toDomain(it) }
    }

    override fun findProximosAVencer(fechaInicio: LocalDate, fechaFin: LocalDate): List<Lote> {
        return jpaLoteRepository.findProximosAVencer(fechaInicio, fechaFin)
            .map { LoteMapper.toDomain(it) }
    }

    override fun findVencidos(fecha: LocalDate): List<Lote> {
        return jpaLoteRepository.findVencidos(fecha)
            .map { LoteMapper.toDomain(it) }
    }

    override fun save(lote: Lote): Lote {
        throw NotImplementedError("Usar el servicio de inventario para crear lotes")
    }

    override fun delete(id: Int) {
        jpaLoteRepository.deleteById(id)
    }
}