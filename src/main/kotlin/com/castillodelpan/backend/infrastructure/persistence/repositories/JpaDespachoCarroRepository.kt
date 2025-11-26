package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.DespachoCarro
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface JpaDespachoCarroRepository : JpaRepository<DespachoCarro, Int> {
    @Query(
        """
        SELECT d FROM DespachoCarro d 
        WHERE d.carro.idCarro = :idCarro 
        AND DATE(d.fechaDespacho) = :fecha
    """
    )
    fun findByCarroAndFecha(
        @Param("idCarro") idCarro: Int,
        @Param("fecha") fecha: LocalDate
    ): List<DespachoCarro>

    @Query(
        """
        SELECT d FROM DespachoCarro d 
        WHERE d.conductor.idConductor = :idConductor 
        AND DATE(d.fechaDespacho) = :fecha
    """
    )
    fun findByConductorAndFecha(
        @Param("idConductor") idConductor: Int,
        @Param("fecha") fecha: LocalDate
    ): List<DespachoCarro>

    @Query(
        """
        SELECT d FROM DespachoCarro d 
        WHERE DATE(d.fechaDespacho) = :fecha
        ORDER BY d.horaSalida
    """
    )
    fun findByFechaDespacho(@Param("fecha") fecha: LocalDate): List<DespachoCarro>
}