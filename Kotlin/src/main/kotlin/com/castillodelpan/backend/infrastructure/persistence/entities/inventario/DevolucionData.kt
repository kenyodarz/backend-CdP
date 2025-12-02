package com.castillodelpan.backend.infrastructure.persistence.entities.inventario

import com.castillodelpan.backend.domain.models.enums.MotivoDevolucion
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
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
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

@Entity
@Table(name = "devoluciones")
data class DevolucionData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_devolucion")
    var idDevolucion: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_despacho", nullable = false)
    @JsonIgnore
    var despacho: DespachoCarro,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    var producto: ProductoData,

    @Column(nullable = false)
    @Min(1)
    var cantidad: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    var motivo: MotivoDevolucion,

    @Column(columnDefinition = "TEXT")
    var observaciones: String? = null,

    @Column(name = "reingresar_stock", nullable = false)
    var reingresarStock: Boolean = true,

    @Column(name = "fecha_devolucion")
    var fechaDevolucion: LocalDateTime = LocalDateTime.now(),

    @Column(name = "id_usuario")
    var idUsuario: Int? = null
) {
    @PrePersist
    fun validarReingreso() {
        // Si el motivo es DANADO o VENCIDO, no se reingresa al stock
        if (motivo in listOf(MotivoDevolucion.DANADO, MotivoDevolucion.VENCIDO)) {
            reingresarStock = false
        }
    }
}