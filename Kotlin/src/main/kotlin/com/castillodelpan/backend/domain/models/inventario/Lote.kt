package com.castillodelpan.backend.domain.models.inventario

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.producto.Producto
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Lote(
    val idLote: Int?,
    val producto: Producto,
    val codigoLote: String,
    val fechaElaboracion: LocalDate,
    val fechaVencimiento: LocalDate?,
    val cantidadInicial: Int,
    var cantidadActual: Int,
    val estado: EstadoGeneral
) {
    /**
     * Calcula los días restantes para vencer
     */
    val diasParaVencer: Long?
        get() = fechaVencimiento?.let {
            ChronoUnit.DAYS.between(LocalDate.now(), it)
        }

    /**
     * Verifica si está próximo a vencer (3 días o menos)
     */
    val estaProximoAVencer: Boolean
        get() = (diasParaVencer ?: Long.MAX_VALUE) <= 3

    /**
     * Verifica si ya está vencido
     */
    val estaVencido: Boolean
        get() = fechaVencimiento?.isBefore(LocalDate.now()) ?: false
}