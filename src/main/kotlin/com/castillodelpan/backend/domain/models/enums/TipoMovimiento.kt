package com.castillodelpan.backend.domain.models.enums

/**
 * Tipos de movimiento de inventario
 */
enum class TipoMovimiento {
    ENTRADA,     // Recepción de producción
    SALIDA,      // Despacho en carro
    AJUSTE,      // Corrección de inventario
    DEVOLUCION   // Productos devueltos de carros
}