package com.castillodelpan.backend.application.dto.commands

import com.castillodelpan.backend.domain.models.enums.TipoTarifa
import java.math.BigDecimal

data class PrecioProductoCommand(
    val tipoTarifa: TipoTarifa,
    val precio: BigDecimal
)