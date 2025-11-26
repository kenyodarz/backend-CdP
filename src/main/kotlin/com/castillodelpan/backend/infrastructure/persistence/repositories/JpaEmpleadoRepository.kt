package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.EmpleadoData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaEmpleadoRepository : JpaRepository<EmpleadoData, Int> {
    fun findByNumeroDocumento(numeroDocumento: String): Optional<EmpleadoData>
    fun findByEstado(estado: EstadoGeneral): List<EmpleadoData>

    @Query("SELECT e FROM EmpleadoData e WHERE e.estado = 'ACTIVO' ORDER BY e.nombres")
    fun findAllActivos(): List<EmpleadoData>
}