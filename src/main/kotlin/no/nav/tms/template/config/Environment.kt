package no.nav.tms.template.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.serialization.jackson.jackson
import io.ktor.server.plugins.contentnegotiation.ContentNegotiationConfig
import no.nav.personbruker.dittnav.common.util.config.StringEnvVar.getEnvVar
import java.text.DateFormat

data class Environment(
    val corsAllowedOrigins: String = getEnvVar("CORS_ALLOWED_ORIGINS"),
    val groupId: String = getEnvVar("GROUP_ID"),
    val dbHost: String = getEnvVar("DB_HOST"),
    val dbPort: String = getEnvVar("DB_PORT"),
    val dbName: String = getEnvVar("DB_DATABASE"),
    val dbUser: String = getEnvVar("DB_USERNAME"),
    val dbPassword: String = getEnvVar("DB_PASSWORD"),
    val dbUrl: String = getDbUrl(dbHost, dbPort, dbName),
    val clusterName: String = getEnvVar("NAIS_CLUSTER_NAME"),
    val namespace: String = getEnvVar("NAIS_NAMESPACE"),
    val aivenBrokers: String = getEnvVar("KAFKA_BROKERS"),
    val aivenSchemaRegistry: String = getEnvVar("KAFKA_SCHEMA_REGISTRY"),
    val securityVars: SecurityVars = SecurityVars(),
    val rapidTopic: String = getEnvVar("RAPID_TOPIC"),
) {

    fun rapidConfig(): Map<String, String> = mapOf(
        "KAFKA_BROKERS" to aivenBrokers,
        "KAFKA_CONSUMER_GROUP_ID" to groupId,
        "KAFKA_RAPID_TOPIC" to rapidTopic,
        "KAFKA_KEYSTORE_PATH" to securityVars.aivenKeystorePath,
        "KAFKA_CREDSTORE_PASSWORD" to securityVars.aivenCredstorePassword,
        "KAFKA_TRUSTSTORE_PATH" to securityVars.aivenTruststorePath,
        "KAFKA_RESET_POLICY" to "earliest",
        "HTTP_PORT" to "8080"
    )
}

data class SecurityVars(
    val aivenTruststorePath: String = getEnvVar("KAFKA_TRUSTSTORE_PATH"),
    val aivenKeystorePath: String = getEnvVar("KAFKA_KEYSTORE_PATH"),
    val aivenCredstorePassword: String = getEnvVar("KAFKA_CREDSTORE_PASSWORD"),
    val aivenSchemaRegistryUser: String = getEnvVar("KAFKA_SCHEMA_REGISTRY_USER"),
    val aivenSchemaRegistryPassword: String = getEnvVar("KAFKA_SCHEMA_REGISTRY_PASSWORD")
)

fun getDbUrl(host: String, port: String, name: String): String {
    return if (host.endsWith(":$port")) {
        "jdbc:postgresql://${host}/$name"
    } else {
        "jdbc:postgresql://${host}:${port}/${name}"
    }
}



fun ContentNegotiationConfig.jsonConfig() = jackson {
    registerModule(JavaTimeModule())
    dateFormat = DateFormat.getDateTimeInstance()
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}
