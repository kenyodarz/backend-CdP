package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.catalogo.Conductor
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Conductores
 */
interface ConductorRepository {
    fun findById(id: Int): Conductor?
    fun findByNumeroDocumento(numeroDocumento: String): Conductor?
    fun findAll(): List<Conductor>
    fun findAllActivos(): List<Conductor>
    fun findByEstado(estado: EstadoGeneral): List<Conductor>
    fun save(conductor: Conductor): Conductor
    fun delete(id: Int)
}