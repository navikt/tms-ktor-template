package no.nav.tms.template.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.jackson.jackson
import java.text.DateFormat


object HttpClientBuilder {

    private val httpClient = HttpClient(Apache.create()) {
        install(ContentNegotiation) {
            jackson {
                registerModule(JavaTimeModule())
                dateFormat = DateFormat.getDateTimeInstance()
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
        }
        install(HttpTimeout)
    }

}