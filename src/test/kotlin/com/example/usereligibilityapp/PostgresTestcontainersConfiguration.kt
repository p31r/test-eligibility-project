package com.example.usereligibilityapp

import com.example.usereligibilityapp.WireMockConfig.Companion.wireMockServer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextClosedEvent
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

//@TestConfiguration(proxyBeanMethods = false)
class PostgresTestcontainersConfiguration : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:16.9"))
        postgresContainer.start()

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                postgresContainer.stop()
            }
        }

        println("\n\n\n---------------\n${postgresContainer.jdbcUrl} | ${postgresContainer.username}\n---------\n\n\n")

        TestPropertyValues.of(
            "spring.datasource.url=${postgresContainer.jdbcUrl}",
            "spring.datasource.username=${postgresContainer.username}",
            "spring.datasource.password=${postgresContainer.password}",
        ).applyTo(applicationContext)
    }

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> = postgresContainer

    companion object {
        lateinit var postgresContainer: PostgreSQLContainer<*>
    }
}