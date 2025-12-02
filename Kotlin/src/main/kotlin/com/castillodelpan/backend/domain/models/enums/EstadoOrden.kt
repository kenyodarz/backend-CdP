package com.castillodelpan.backend.domain.models.enums

/**
 * Estados de órdenes de despacho
 */
enum class EstadoOrden {
    PENDIENTE,        // Orden creada, sin preparar
    EN_PREPARACION,   // Bodeguero preparando productos
    LISTA,            // Lista para despachar
    DESPACHADA,       // Cargada en carro y despachada
    ENTREGADA,        // Entregada al cliente
    CANCELADA         // Orden cancelada
}