package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.Cliente
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Clientes
 */
interface ClienteRepository {
    fun findById(id: Int): Cliente?
    fun findByCodigo(codigo: String): Cliente?
    fun findByNumeroDocumento(numeroDocumento: String): Cliente?
    fun findAll(): List<Cliente>
    fun findByEstado(estado: EstadoGeneral): List<Cliente>
    fun findByEstadoActivo(): List<Cliente>
    fun findByEstadoInactivo(): List<Cliente>
    fun findByRuta(idRuta: Int): List<Cliente>
    fun findByBarrio(barrio: String): List<Cliente>
    fun findByNombreContaining(nombre: String): List<Cliente>
    fun save(cliente: Cliente): Cliente
    fun delete(id: Int)
    fun existsByNumeroDocumento(numeroDocumento: String): Boolean
}