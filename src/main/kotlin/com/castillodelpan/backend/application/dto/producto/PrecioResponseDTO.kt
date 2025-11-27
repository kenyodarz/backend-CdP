package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.domain.models.producto.PrecioProducto
import java.math.BigDecimal

/**
 * DTO de respuesta para precio
 */
data class PrecioResponseDTO(
    val idPrecio: Int,
    val tipoTarifa: TipoTarifa,
    val precio: BigDecimal
) {
    companion object {
        fun fromDomain(precio: PrecioProducto): PrecioResponseDTO {
            return PrecioResponseDTO(
                idPrecio = precio.idPrecio!!,
                tipoTarifa = precio.tipoTarifa,
                precio = precio.precio
            )
        }
    }
}