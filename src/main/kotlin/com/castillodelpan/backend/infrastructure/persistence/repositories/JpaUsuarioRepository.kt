package com.castillodelpan.backend.infrastructure.persistence.repositories

import com.castillodelpan.backend.infrastructure.persistence.entities.login.UsuarioData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaUsuarioRepository : JpaRepository<UsuarioData, Int> {
    fun findByUsername(username: String): Optional<UsuarioData>
    fun existsByUsername(username: String): Boolean

    @Query(
        """
        SELECT u FROM UsuarioData u 
        WHERE u.empleado.idEmpleado = :idEmpleado
    """
    )
    fun findByEmpleadoId(@Param("idEmpleado") idEmpleado: Int): Optional<UsuarioData>
}