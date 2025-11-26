package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.infrastructure.persistence.entities.core.DetalleOrdenData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaDetalleOrdenRepository : JpaRepository<DetalleOrdenData, Int> {
    @Query(
        """
        SELECT d FROM DetalleOrdenData d 
        WHERE d.orden.idOrden = :idOrden
    """
    )
    fun findByOrdenId(@Param("idOrden") idOrden: Int): List<DetalleOrdenData>
}