package com.castillodelpan.backend.infrastructure.web.dto

/**
 * Wrapper para respuestas paginadas
 */
data class PageResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isFirst: Boolean,
    val isLast: Boolean
)