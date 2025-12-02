package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.Ruta
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.RutaRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.RutaMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaRutaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class RutaRepositoryImpl(
    private val jpaRutaRepository: JpaRutaRepository
) : RutaRepository {

    override fun findById(id: Int): Ruta? {
        return jpaRutaRepository.findById(id)
            .map { RutaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNombre(nombre: String): Ruta? {
        return jpaRutaRepository.findByNombre(nombre)
            .map { RutaMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Ruta> {
        return jpaRutaRepository.findAll()
            .map { RutaMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Ruta> {
        return jpaRutaRepository.findByEstado(estado)
            .map { RutaMapper.toDomain(it) }
    }

    override fun save(ruta: Ruta): Ruta {
        val rutaData = RutaMapper.toData(ruta)
        val savedData = jpaRutaRepository.save(rutaData)
        return RutaMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaRutaRepository.deleteById(id)
    }
}