package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.Ruta
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Rutas
 */
interface RutaRepository {
    fun findById(id: Int): Ruta?
    fun findByNombre(nombre: String): Ruta?
    fun findAll(): List<Ruta>
    fun findByEstado(estado: EstadoGeneral): List<Ruta>
    fun save(ruta: Ruta): Ruta
    fun delete(id: Int)
}