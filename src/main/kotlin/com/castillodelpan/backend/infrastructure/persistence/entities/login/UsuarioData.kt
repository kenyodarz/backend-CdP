package com.castillodelpan.backend.infrastructure.persistence.entities.login

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.RolUsuario
import com.castillodelpan.backend.infrastructure.persistence.entities.core.EmpleadoData
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "usuarios")
data class UsuarioData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    var idUsuario: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    var empleado: EmpleadoData,

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    var username: String,

    @Column(name = "password_hash", nullable = false, length = 255)
    @NotBlank
    @JsonIgnore
    var passwordHash: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var rol: RolUsuario,

    @Column(name = "ultimo_acceso")
    var ultimoAcceso: LocalDateTime? = null,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
)