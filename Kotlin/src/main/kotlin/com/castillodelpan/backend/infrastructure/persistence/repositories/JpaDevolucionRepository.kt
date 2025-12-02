package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.DevolucionData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface JpaDevolucionRepository : JpaRepository<DevolucionData, Int> {
    @Query(
        """
        SELECT d FROM DevolucionData d 
        WHERE d.despacho.idDespacho = :idDespacho
    """
    )
    fun findByDespachoId(@Param("idDespacho") idDespacho: Int): List<DevolucionData>

    @Query(
        """
        SELECT d FROM DevolucionData d 
        WHERE DATE(d.fechaDevolucion) = :fecha
        ORDER BY d.fechaDevolucion DESC
    """
    )
    fun findByFecha(@Param("fecha") fecha: LocalDate): List<DevolucionData>

    @Query(
        """
        SELECT d FROM DevolucionData d 
        WHERE d.producto.idProducto = :idProducto 
        AND DATE(d.fechaDevolucion) = :fecha
    """
    )
    fun findByProductoAndFecha(
        @Param("idProducto") idProducto: Int,
        @Param("fecha") fecha: LocalDate
    ): List<DevolucionData>
}