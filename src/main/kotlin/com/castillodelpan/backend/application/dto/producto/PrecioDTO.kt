package com.castillodelpan.backend.application.dto.producto

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import com.castillodelpan.backend.domain.models.producto.PrecioProducto
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

/**
 * DTO para precios de producto
 */
data class PrecioDTO(
    @field:NotNull(message = "El tipo de tarifa es obligatorio")
    val tipoTarifa: TipoTarifa,

    @field:NotNull(message = "El precio es obligatorio")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    val precio: BigDecimal
) {
    fun toDomain(): PrecioProducto {
        return PrecioProducto(
            idPrecio = null,
            tipoTarifa = tipoTarifa,
            precio = precio
        )
    }
}