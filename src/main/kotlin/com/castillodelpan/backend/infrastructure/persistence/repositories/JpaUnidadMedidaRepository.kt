package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.UnidadMedidaData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaUnidadMedidaRepository : JpaRepository<UnidadMedidaData, Int> {
    fun findByCodigo(codigo: String): Optional<UnidadMedidaData>
    fun findByEstado(estado: EstadoGeneral): List<UnidadMedidaData>
}