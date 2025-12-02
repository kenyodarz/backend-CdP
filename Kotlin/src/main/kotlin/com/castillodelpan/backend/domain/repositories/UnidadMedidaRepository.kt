package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.UnidadMedida
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Unidades de Medida
 */
interface UnidadMedidaRepository {
    fun findById(id: Int): UnidadMedida?
    fun findByCodigo(codigo: String): UnidadMedida?
    fun findAll(): List<UnidadMedida>
    fun findByEstado(estado: EstadoGeneral): List<UnidadMedida>
    fun save(unidadMedida: UnidadMedida): UnidadMedida
    fun delete(id: Int)
}