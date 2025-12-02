package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoOrden
import com.castillodelpan.backend.infrastructure.persistence.entities.core.OrdenDespachoData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface JpaOrdenDespachoRepository : JpaRepository<OrdenDespachoData, Int> {
    fun findByNumeroOrden(numeroOrden: String): Optional<OrdenDespachoData>

    @Query(
        """
        SELECT o FROM OrdenDespachoData o 
        WHERE o.fechaOrden = :fecha 
        ORDER BY o.numeroOrden
    """
    )
    fun findByFechaOrden(@Param("fecha") fecha: LocalDate): List<OrdenDespachoData>

    @Query(
        """
        SELECT o FROM OrdenDespachoData o 
        WHERE o.estado = :estado 
        ORDER BY o.fechaOrden DESC
    """
    )
    fun findByEstado(@Param("estado") estado: EstadoOrden): List<OrdenDespachoData>

    @Query(
        """
        SELECT o FROM OrdenDespachoData o 
        WHERE o.cliente.idCliente = :idCliente 
        ORDER BY o.fechaOrden DESC
    """
    )
    fun findByCliente(@Param("idCliente") idCliente: Int): List<OrdenDespachoData>

    @Query(
        """
        SELECT o FROM OrdenDespachoData o 
        WHERE o.fechaOrden BETWEEN :fechaInicio AND :fechaFin 
        ORDER BY o.fechaOrden DESC
    """
    )
    fun findByFechaOrdenBetween(
        @Param("fechaInicio") fechaInicio: LocalDate,
        @Param("fechaFin") fechaFin: LocalDate
    ): List<OrdenDespachoData>

    @Query(
        """
        SELECT o FROM OrdenDespachoData o 
        WHERE o.fechaOrden = :fecha 
        AND o.estado = :estado
        ORDER BY o.numeroOrden
    """
    )
    fun findByFechaAndEstado(
        @Param("fecha") fecha: LocalDate,
        @Param("estado") estado: EstadoOrden
    ): List<OrdenDespachoData>

    @Query(
        """
        SELECT COUNT(o) FROM OrdenDespachoData o 
        WHERE o.fechaOrden = :fecha 
        AND o.estado IN ('PENDIENTE', 'EN_PREPARACION')
    """
    )
    fun contarOrdenesPendientesDelDia(@Param("fecha") fecha: LocalDate): Long
}