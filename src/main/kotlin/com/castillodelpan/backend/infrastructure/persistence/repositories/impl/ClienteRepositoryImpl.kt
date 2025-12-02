package com.castillodelpan.backend.infrastructure.persistence.repositories.impl

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.repositories.ClienteRepository
import com.castillodelpan.backend.infrastructure.persistence.mappers.ClienteMapper
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaClienteRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaConductorRepository
import com.castillodelpan.backend.infrastructure.persistence.repositories.JpaRutaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class ClienteRepositoryImpl(
    private val jpaClienteRepository: JpaClienteRepository,
    private val jpaRutaRepository: JpaRutaRepository,
    private val jpaConductorRepository: JpaConductorRepository
) : ClienteRepository {

    override fun findById(id: Int): Cliente? {
        return jpaClienteRepository.findById(id).map { ClienteMapper.toDomain(it) }.orElse(null)
    }

    override fun findByCodigo(codigo: String): Cliente? {
        return jpaClienteRepository
            .findByCodigo(codigo)
            .map { ClienteMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByNumeroDocumento(numeroDocumento: String): Cliente? {
        return jpaClienteRepository
            .findByNumeroDocumento(numeroDocumento)
            .map { ClienteMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): List<Cliente> {
        return jpaClienteRepository.findAll().map { ClienteMapper.toDomain(it) }
    }

    override fun findByEstado(estado: EstadoGeneral): List<Cliente> {
        return jpaClienteRepository.findByEstado(estado).map { ClienteMapper.toDomain(it) }
    }

    override fun findByRuta(idRuta: Int): List<Cliente> {
        return jpaClienteRepository.findByRuta(idRuta).map { ClienteMapper.toDomain(it) }
    }

    override fun findByBarrio(barrio: String): List<Cliente> {
        return jpaClienteRepository.findByBarrio(barrio).map { ClienteMapper.toDomain(it) }
    }

    override fun findByNombreContaining(nombre: String): List<Cliente> {
        return jpaClienteRepository.findByNombreContainingIgnoreCase(nombre).map {
            ClienteMapper.toDomain(it)
        }
    }

    override fun save(cliente: Cliente): Cliente {
        val rutaData = cliente.ruta?.let { jpaRutaRepository.findById(it.idRuta!!).orElse(null) }

        val conductorData =
            cliente.conductor?.let {
                jpaConductorRepository.findById(it.idConductor!!).orElse(null)
            }

        val clienteData = ClienteMapper.toData(cliente, rutaData, conductorData)
        val savedData = jpaClienteRepository.save(clienteData)
        return ClienteMapper.toDomain(savedData)
    }

    override fun delete(id: Int) {
        jpaClienteRepository.deleteById(id)
    }

    override fun existsByNumeroDocumento(numeroDocumento: String): Boolean {
        return jpaClienteRepository.existsByNumeroDocumento(numeroDocumento)
    }
}
