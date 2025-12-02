package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.infrastructure.persistence.entities.core.PrecioProductoData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaPrecioProductoRepository : JpaRepository<PrecioProductoData, Int> {
    fun findByProductoAndTipoTarifa(
        producto: ProductoData,
        tipoTarifa: TipoTarifa
    ): Optional<PrecioProductoData>

    @Query(
        """
        SELECT p FROM PrecioProductoData p 
        WHERE p.producto.idProducto = :idProducto
    """
    )
    fun findByProductoId(@Param("idProducto") idProducto: Int): List<PrecioProductoData>
}