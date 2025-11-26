package com.castillodelpan.backend.domain.models.enums

/**
 * Tarifas/Precios según tipo de cliente
 * Basado en el sistema actual de El Castillo del Pan
 */
enum class TipoTarifa(val descripcion: String, val descuento: Int) {
    PRECIO_0D("Precio Full - Sin descuento", 0),
    PRECIO_10D("Precio con 10% descuento", 10),
    PRECIO_5D("Precio con 5% descuento", 5),
    PRECIO_ES("Precio Especial PyF", 0),
    PRECIO_JM("Precio JM", 0),
    PRECIO_CR("Precio CR", 0);

    companion object {
        fun fromCode(code: String): TipoTarifa {
            return when (code.uppercase()) {
                "0D" -> PRECIO_0D
                "10D" -> PRECIO_10D
                "5D" -> PRECIO_5D
                "ES" -> PRECIO_ES
                "JM" -> PRECIO_JM
                "CR" -> PRECIO_CR
                else -> PRECIO_0D
            }
        }
    }
}