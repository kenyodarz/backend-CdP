package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.UnidadMedidaRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.UnidadMedidaMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaUnidadMedidaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class UnidadMedidaRepositoryImpl(
    private val jpaUnidadMedidaRepository: JpaUnidadMedidaRepository
) : UnidadMedidaRepository {

    override fun findById(id: Int): UnidadMedida? {
        return jpaUnidadMedidaRepository.findById(id)
            .map { UnidadMedidaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByCodigo(codigo: String): UnidadMedida? {
        return jpaUnidadMedidaRepository.findByCodigo(codigo)
            .map { UnidadMedidaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<UnidadMedida> {
        return jpaUnidadMedidaRepository.findAll()
            .map { UnidadMedidaMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<UnidadMedida> {
        return jpaUnidadMedidaRepository.findByEstado(estado)
            .map { UnidadMedidaMapper.toDomain(it) }
    }

    override fun save(unidadMedida: UnidadMedida): UnidadMedida {
        val unidadData = UnidadMedidaMapper.toData(unidadMedida)
        val savedData = jpaUnidadMedidaRepository.save(unidadData)
        return UnidadMedidaMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaUnidadMedidaRepository.deleteById(id)
    }
}