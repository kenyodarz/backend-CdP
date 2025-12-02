package com.castillodelpan.backend.infrastructure.persistence.entities.inventario

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral.ACTIVO
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ProductoData
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
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "lotes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["id_producto", "codigo_lote"])]
)
data class LoteData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    var idLote: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    var producto: ProductoData,

    @Column(name = "codigo_lote", nullable = false, length = 50)
    @NotBlank
    var codigoLote: String,

    @Column(name = "fecha_elaboracion", nullable = false)
    var fechaElaboracion: LocalDate,

    @Column(name = "fecha_vencimiento")
    var fechaVencimiento: LocalDate? = null,

    @Column(name = "cantidad_inicial", nullable = false)
    @Min(1)
    var cantidadInicial: Int,

    @Column(name = "cantidad_actual", nullable = false)
    var cantidadActual: Int,

    @Enumerated(EnumType.STRING)
    var estado: EstadoGeneral = ACTIVO,

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
) {
    val diasParaVencer: Long?
        get() = fechaVencimiento?.let {
            java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), it)
        }

    val estaProximoAVencer: Boolean
        get() = (diasParaVencer ?: Long.MAX_VALUE) <= 3

    val estaVencido: Boolean
        get() = fechaVencimiento?.isBefore(LocalDate.now()) ?: false
}