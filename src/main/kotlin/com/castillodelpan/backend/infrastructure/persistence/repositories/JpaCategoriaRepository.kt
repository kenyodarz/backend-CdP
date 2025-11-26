package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CategoriaData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaCategoriaRepository : JpaRepository<CategoriaData, Int> {
    fun findByNombre(nombre: String): Optional<CategoriaData>
    fun findByEstado(estado: EstadoGeneral): List<CategoriaData>
}