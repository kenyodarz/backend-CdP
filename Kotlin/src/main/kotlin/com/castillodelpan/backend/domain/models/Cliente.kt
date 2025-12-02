package com.castillodelpan.backend.domain.models

import com.castillodelpan.backend.domain.models.catalogo.Conductor
import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.enums.TipoDocumento
import com.castillodelpan.backend.domain.models.enums.TipoTarifa

data class Cliente(
    val idCliente: Int?,
    val nombre: String,
    val codigo: String?,
    val tipoDocumento: TipoDocumento?,
    val numeroDocumento: String?,
    val direccion: String?,
    val telefono: String?,
    val barrio: String?,
    val comuna: String?,
    val tipoNegocio: String?,
    val tipoTarifa: TipoTarifa,
    val ruta: Ruta?,
    val conductor: Conductor?,
    val horarioEntrega: String?,
    val estado: EstadoGeneral
)