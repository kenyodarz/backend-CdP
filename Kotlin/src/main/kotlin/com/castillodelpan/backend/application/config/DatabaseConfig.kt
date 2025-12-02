// ============================================
// 3. DatabaseConfig.kt
// ============================================
package com.castillodelpan.backend.application.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["com.castillodelpan.backend.infrastructure.persistence.repositories"])
@EnableJpaAuditing
@EnableTransactionManagement
class DatabaseConfig {
    // Configuración adicional de base de datos si es necesaria
}