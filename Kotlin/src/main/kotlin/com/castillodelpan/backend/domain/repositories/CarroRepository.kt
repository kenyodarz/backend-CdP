package com.castillodelpan.backend.domain.repositories

import com.castillodelpan.backend.domain.models.catalogo.Carro
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral

/**
 * Repositorio de Carros
 */
interface CarroRepository {
    fun findById(id: Int): Carro?
    fun findByPlaca(placa: String): Carro?
    fun findAll(): List<Carro>
    fun findAllActivos(): List<Carro>
    fun findByEstado(estado: EstadoGeneral): List<Carro>
    fun save(carro: Carro): Carro
    fun delete(id: Int)
}