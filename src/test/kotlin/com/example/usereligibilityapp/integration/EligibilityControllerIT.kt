package com.example.usereligibilityapp.integration

import com.example.usereligibilityapp.PostgresTestcontainersConfiguration
import com.example.usereligibilityapp.WireMockConfig
import com.example.usereligibilityapp.WireMockConfig.Companion.wireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import java.util.*
import kotlin.test.Test

// TODO test is not working!!!

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(initializers = [WireMockConfig::class/*, PostgresTestcontainersConfiguration::class*/])
class EligibilityControllerIT {

    @Autowired
    lateinit var webClient: WebTestClient

    private val mockClientId = "1234-56-78-90.12.34.567890"

    companion object {

        @Container
        val postgresContainer = PostgreSQLContainer(DockerImageName.parse("postgres:16.9"))
    }


    @BeforeEach
    fun clearWireMock() {
        wireMockServer.resetAll()
    }

    @Test
    fun `eligible client should return 200 OK with eligible true`() {
        TODO("Test is not working, won´t load test class")
        stubFor(
            get(urlEqualTo("/$mockClientId"))
                .willReturn(
                    okJson(
                        """
                    {
                      "birthDate": "1954-07-04",
                      "clientVerificationLevel": 3,
                      "forename": "ValidUser",
                      "primaryEmail": "test5961813@csas.cz",
                      "gender": "M",
                      "primaryPhone": "420953186843",
                      "pep": false,
                      "verifiedBy": "45244782",
                      "surname": "Moravec",
                      "clientId": "1234-56-78-90.12.34.567890"
                    }
                """
                    )
                )
        )

        stubFor(
            get(urlEqualTo("/list"))
                .withHeader("clientId", equalTo(mockClientId))
                .willReturn(
                    okJson(
                        """
                    {
                        "client": {
                          "forename": "Filip",
                          "surname": "Moravec",
                          "clientId": "1234-56-78-90.12.34.567890"
                        },
                        "accounts": [
                          {
                            "product_id": "SB0_22291",
                            "iban": "CZ3908000000000735147003",
                            "currency": "CZK"
                          }
                        ]
                    }
                """
                    )
                )
        )

        val result = webClient.get()
            .uri("/api/v1/eligibility")
            .header("clientId", mockClientId)
            .header("correlation-id", UUID.randomUUID().toString())
            .exchange()
            .expectBody()
            .returnResult().responseBody

        println("Done")
    }
}
