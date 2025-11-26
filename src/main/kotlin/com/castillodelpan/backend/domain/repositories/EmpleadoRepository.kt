package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.Empleado
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Empleados
 */
interface EmpleadoRepository {
    fun findById(id: Int): Empleado?
    fun findByNumeroDocumento(numeroDocumento: String): Empleado?
    fun findAll(): List<Empleado>
    fun findAllActivos(): List<Empleado>
    fun findByEstado(estado: EstadoGeneral): List<Empleado>
    fun save(empleado: Empleado): Empleado
    fun delete(id: Int)
}