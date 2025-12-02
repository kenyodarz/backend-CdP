package com.castillodelpan.backend.infrastructure.persistence.entities.core

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.infrastructure.persistence.converters.EstadoGeneralConverter
import com.castillodelpan.backend.infrastructure.persistence.converters.TipoTarifaConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
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

@Entity
@Table(name = "clientes")
data class ClienteData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    var idCliente: Int? = null,

    @Column(nullable = false, length = 200)
    @NotBlank
    var nombre: String,

    @Column(unique = true, length = 50)
    var codigo: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 20)
    var tipoDocumento: TipoDocumento? = null,

    @Column(name = "numero_documento", length = 20)
    var numeroDocumento: String? = null,

    @Column(columnDefinition = "TEXT")
    var direccion: String? = null,

    @Column(length = 20)
    var telefono: String? = null,

    @Column(length = 100)
    var barrio: String? = null,

    @Column(length = 50)
    var comuna: String? = null,

    @Column(name = "tipo_negocio", length = 100)
    var tipoNegocio: String? = null,

    @Convert(converter = TipoTarifaConverter::class)
    @Column(name = "tipo_tarifa", length = 20)
    var tipoTarifa: TipoTarifa = TipoTarifa.PRECIO_0D,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta")
    var ruta: RutaData? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conductor")
    var conductor: ConductorData? = null,

    @Column(name = "horario_entrega", length = 50)
    var horarioEntrega: String? = null,

    @Convert(converter = EstadoGeneralConverter::class)
    var estado: EstadoGeneral = EstadoGeneral.ACTIVO
) : EntidadAuditable()
