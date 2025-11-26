package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ConductorData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaConductorRepository : JpaRepository<ConductorData, Int> {
    fun findByNumeroDocumento(numeroDocumento: String): Optional<ConductorData>
    fun findByEstado(estado: EstadoGeneral): List<ConductorData>

    @Query("SELECT c FROM ConductorData c WHERE c.estado = 'ACTIVO' ORDER BY c.nombres")
    fun findAllActivos(): List<ConductorData>
}