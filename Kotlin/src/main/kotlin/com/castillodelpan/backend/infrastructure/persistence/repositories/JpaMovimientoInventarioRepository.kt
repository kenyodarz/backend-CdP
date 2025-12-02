package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.TipoMovimiento
import com.castillodelpan.backend.infrastructure.persistence.entities.inventario.MovimientoInventario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface JpaMovimientoInventarioRepository : JpaRepository<MovimientoInventario, Int> {
    @Query(
        """
        SELECT m FROM MovimientoInventario m 
        WHERE m.producto.idProducto = :idProducto 
        ORDER BY m.fechaMovimiento DESC
    """
    )
    fun findByProductoId(@Param("idProducto") idProducto: Int): List<MovimientoInventario>

    @Query(
        """
        SELECT m FROM MovimientoInventario m 
        WHERE DATE(m.fechaMovimiento) = :fecha 
        ORDER BY m.fechaMovimiento DESC
    """
    )
    fun findByFecha(@Param("fecha") fecha: LocalDate): List<MovimientoInventario>

    @Query(
        """
        SELECT m FROM MovimientoInventario m 
        WHERE m.tipoMovimiento = :tipo 
        AND DATE(m.fechaMovimiento) = :fecha
        ORDER BY m.fechaMovimiento DESC
    """
    )
    fun findByTipoAndFecha(
        @Param("tipo") tipo: TipoMovimiento,
        @Param("fecha") fecha: LocalDate
    ): List<MovimientoInventario>
}