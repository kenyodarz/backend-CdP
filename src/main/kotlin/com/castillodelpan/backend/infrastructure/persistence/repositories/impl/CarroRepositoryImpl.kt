package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.catalogo.Carro
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.CarroRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.CarroMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaCarroRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class CarroRepositoryImpl(
    private val jpaCarroRepository: JpaCarroRepository
) : CarroRepository {

    override fun findById(id: Int): Carro? {
        return jpaCarroRepository.findById(id)
            .map { CarroMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByPlaca(placa: String): Carro? {
        return jpaCarroRepository.findByPlaca(placa)
            .map { CarroMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Carro> {
        return jpaCarroRepository.findAll()
            .map { CarroMapper.toDomain(it) }
    }

    override fun findAllActivos(): List<Carro> {
        return jpaCarroRepository.findAllActivos()
            .map { CarroMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Carro> {
        return jpaCarroRepository.findByEstado(estado)
            .map { CarroMapper.toDomain(it) }
    }

    override fun save(carro: Carro): Carro {
        val carroData = CarroMapper.toData(carro)
        val savedData = jpaCarroRepository.save(carroData)
        return CarroMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaCarroRepository.deleteById(id)
    }
}