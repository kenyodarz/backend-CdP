package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.Categoria
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Categorías
 */
interface CategoriaRepository {
    fun findById(id: Int): Categoria?
    fun findByNombre(nombre: String): Categoria?
    fun findAll(): List<Categoria>
    fun findByEstado(estado: EstadoGeneral): List<Categoria>
    fun save(categoria: Categoria): Categoria
    fun delete(id: Int)
}