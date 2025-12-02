package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ClienteData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaClienteRepository : JpaRepository<ClienteData, Int> {
    fun findByNombreContainingIgnoreCase(nombre: String): List<ClienteData>
    fun findByNumeroDocumento(numeroDocumento: String): Optional<ClienteData>
    fun findByCodigo(codigo: String): Optional<ClienteData>
    fun existsByNumeroDocumento(numeroDocumento: String): Boolean

    @Query(
        """
        SELECT c FROM ClienteData c 
        WHERE c.estado = :estado 
        ORDER BY c.nombre
    """
    )
    fun findByEstado(@Param("estado") estado: EstadoGeneral): List<ClienteData>

    @Query(
        """
        SELECT c FROM ClienteData c 
        WHERE c.ruta.idRuta = :idRuta 
        AND c.estado = com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
        ORDER BY c.nombre
    """
    )
    fun findByRuta(@Param("idRuta") idRuta: Int): List<ClienteData>

    @Query(
        """
        SELECT c FROM ClienteData c 
        WHERE c.barrio = :barrio 
        AND c.estado = com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
        ORDER BY c.nombre
    """
    )
    fun findByBarrio(@Param("barrio") barrio: String): List<ClienteData>
}
