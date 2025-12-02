package com.castillodelpan.backend.domain.models.producto

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import java.math.BigDecimal

data class PrecioProducto(
    val idPrecio: Int?,
    val tipoTarifa: TipoTarifa,
    val precio: BigDecimal
)