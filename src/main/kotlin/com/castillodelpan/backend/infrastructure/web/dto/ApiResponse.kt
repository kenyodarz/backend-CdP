package com.castillodelpan.backend.infrastructure.web.dto

import java.time.LocalDateTime

/**
 * Wrapper estándar para respuestas exitosas
 */
data class ApiResponse<T>(
    val success: Boolean = true,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T, message: String? = null): ApiResponse<T> {
            return ApiResponse(
                success = true,
                data = data,
                message = message
            )
        }

        fun error(message: String): ApiResponse<Nothing> {
            return ApiResponse(
                success = false,
                message = message
            )
        }
    }
}