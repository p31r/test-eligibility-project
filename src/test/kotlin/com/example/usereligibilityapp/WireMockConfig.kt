package com.example.usereligibilityapp

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextClosedEvent

@TestConfiguration
class WireMockConfig : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())
        wireMockServer.start()

        applicationContext.addApplicationListener {
            if (it is ContextClosedEvent) {
                wireMockServer.stop()
            }
        }

        TestPropertyValues.of(
            "external.clients.baseUrl=http://localhost:${wireMockServer.port()}/",
            "external.accounts.baseUrl=http://localhost:${wireMockServer.port()}/"
        ).applyTo(applicationContext)
    }

    @Bean
    fun wireMockServer(): WireMockServer {
        return wireMockServer
    }

    companion object {
        lateinit var wireMockServer: WireMockServer
    }

}