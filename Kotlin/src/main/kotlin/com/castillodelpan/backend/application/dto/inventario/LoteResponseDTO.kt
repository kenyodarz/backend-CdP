package com.castillodelpan.backend.application.dto.inventario

import com.castillodelpan.backend.domain.models.enums.EstadoGeneral
import com.castillodelpan.backend.domain.models.inventario.Lote
import java.time.LocalDate

data class LoteResponseDTO(
    val idLote: Int,
    val productoNombre: String,
    val codigoLote: String,
    val fechaElaboracion: LocalDate,
    val fechaVencimiento: LocalDate?,
    val cantidadInicial: Int,
    val cantidadActual: Int,
    val estado: EstadoGeneral,
    val diasParaVencer: Long?,
    val estaProximoAVencer: Boolean,
    val estaVencido: Boolean
) {
    companion object {
        fun fromDomain(lote: Lote): LoteResponseDTO {
            return LoteResponseDTO(
                idLote = lote.idLote!!,
                productoNombre = lote.producto.nombre,
                codigoLote = lote.codigoLote,
                fechaElaboracion = lote.fechaElaboracion,
                fechaVencimiento = lote.fechaVencimiento,
                cantidadInicial = lote.cantidadInicial,
                cantidadActual = lote.cantidadActual,
                estado = lote.estado,
                diasParaVencer = lote.diasParaVencer,
                estaProximoAVencer = lote.estaProximoAVencer,
                estaVencido = lote.estaVencido
            )
        }
    }
}