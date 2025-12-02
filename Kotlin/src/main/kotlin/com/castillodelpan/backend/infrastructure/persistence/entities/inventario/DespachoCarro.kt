package com.castillodelpan.backend.infrastructure.persistence.entities.inventario

import com.castillodelpan.backend.infrastructure.persistence.entities.core.CarroData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.ConductorData
import com.castillodelpan.backend.infrastructure.persistence.entities.core.OrdenDespachoData
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "despachos_carros")
data class DespachoCarro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despacho")
    var idDespacho: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    var orden: OrdenDespachoData,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carro", nullable = false)
    var carro: CarroData,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conductor", nullable = false)
    var conductor: ConductorData,

    @Column(name = "fecha_despacho")
    var fechaDespacho: LocalDateTime = LocalDateTime.now(),

    @Column(name = "hora_salida")
    var horaSalida: LocalTime? = null,

    @Column(name = "hora_retorno")
    var horaRetorno: LocalTime? = null,

    @Column(columnDefinition = "TEXT")
    var observaciones: String? = null,

    @Column(name = "id_usuario_despacho")
    var idUsuarioDespacho: Int? = null,

    @OneToMany(mappedBy = "despacho", cascade = [CascadeType.ALL])
    var devoluciones: MutableList<DevolucionData> = mutableListOf()
) {
    val tieneDevoluciones: Boolean
        get() = devoluciones.isNotEmpty()

    val cantidadDevuelta: Int
        get() = devoluciones.sumOf { it.cantidad }
}
