package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.LoteData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface JpaLoteRepository : JpaRepository<LoteData, Int> {
    fun findByCodigoLote(codigoLote: String): Optional<LoteData>

    @Query(
        """
        SELECT l FROM LoteData l 
        WHERE l.producto.idProducto = :idProducto 
        AND l.estado = 'ACTIVO'
        ORDER BY l.fechaVencimiento ASC
    """
    )
    fun findByProductoIdActivos(@Param("idProducto") idProducto: Int): List<LoteData>

    @Query(
        """
        SELECT l FROM LoteData l 
        WHERE l.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin 
        AND l.cantidadActual > 0 
        AND l.estado = 'ACTIVO'
        ORDER BY l.fechaVencimiento ASC
    """
    )
    fun findProximosAVencer(
        @Param("fechaInicio") fechaInicio: LocalDate,
        @Param("fechaFin") fechaFin: LocalDate
    ): List<LoteData>

    @Query(
        """
        SELECT l FROM LoteData l 
        WHERE l.fechaVencimiento < :fecha 
        AND l.cantidadActual > 0 
        AND l.estado = 'ACTIVO'
    """
    )
    fun findVencidos(@Param("fecha") fecha: LocalDate): List<LoteData>
}
