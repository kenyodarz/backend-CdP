package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.Empleado
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.EmpleadoRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.EmpleadoMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaEmpleadoRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class EmpleadoRepositoryImpl(
    private val jpaEmpleadoRepository: JpaEmpleadoRepository
) : EmpleadoRepository {

    override fun findById(id: Int): Empleado? {
        return jpaEmpleadoRepository.findById(id)
            .map { EmpleadoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNumeroDocumento(numeroDocumento: String): Empleado? {
        return jpaEmpleadoRepository.findByNumeroDocumento(numeroDocumento)
            .map { EmpleadoMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Empleado> {
        return jpaEmpleadoRepository.findAll()
            .map { EmpleadoMapper.toDomain(it) }
    }

    override fun findAllActivos(): List<Empleado> {
        return jpaEmpleadoRepository.findAllActivos()
            .map { EmpleadoMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Empleado> {
        return jpaEmpleadoRepository.findByEstado(estado)
            .map { EmpleadoMapper.toDomain(it) }
    }

    override fun save(empleado: Empleado): Empleado {
        val empleadoData = EmpleadoMapper.toData(empleado)
        val savedData = jpaEmpleadoRepository.save(empleadoData)
        return EmpleadoMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaEmpleadoRepository.deleteById(id)
    }
}