package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.catalogo.Conductor
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.ConductorRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.ConductorMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaConductorRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ConductorRepositoryImpl(
    private val jpaConductorRepository: JpaConductorRepository
) : ConductorRepository {

    override fun findById(id: Int): Conductor? {
        return jpaConductorRepository.findById(id)
            .map { ConductorMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNumeroDocumento(numeroDocumento: String): Conductor? {
        return jpaConductorRepository.findByNumeroDocumento(numeroDocumento)
            .map { ConductorMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Conductor> {
        return jpaConductorRepository.findAll()
            .map { ConductorMapper.toDomain(it) }
    }

    override fun findAllActivos(): List<Conductor> {
        return jpaConductorRepository.findAllActivos()
            .map { ConductorMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Conductor> {
        return jpaConductorRepository.findByEstado(estado)
            .map { ConductorMapper.toDomain(it) }
    }

    override fun save(conductor: Conductor): Conductor {
        val conductorData = ConductorMapper.toData(conductor)
        val savedData = jpaConductorRepository.save(conductorData)
        return ConductorMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaConductorRepository.deleteById(id)
    }
}