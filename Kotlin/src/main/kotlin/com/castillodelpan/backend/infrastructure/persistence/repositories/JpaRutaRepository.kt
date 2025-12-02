package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.RutaData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaRutaRepository : JpaRepository<RutaData, Int> {
    fun findByNombre(nombre: String): Optional<RutaData>
    fun findByEstado(estado: EstadoGeneral): List<RutaData>
}