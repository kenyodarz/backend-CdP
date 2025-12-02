package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.CarroData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaCarroRepository : JpaRepository<CarroData, Int> {
    fun findByPlaca(placa: String): Optional<CarroData>
    fun findByEstado(estado: EstadoGeneral): List<CarroData>

    @Query("SELECT c FROM CarroData c WHERE c.estado = 'ACTIVO' ORDER BY c.placa")
    fun findAllActivos(): List<CarroData>
}