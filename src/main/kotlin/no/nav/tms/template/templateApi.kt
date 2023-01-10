package no.nav.tms.template

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.util.pipeline.PipelineContext
import no.nav.tms.template.health.healthApi
import no.nav.tms.token.support.authentication.installer.installAuthenticators
import no.nav.tms.token.support.tokenx.validation.user.TokenXUserFactory
import java.text.DateFormat

internal fun Application.templateApi(installAuthenticatorsFunction: Application.() -> Unit = installAuth()) {

    installAuthenticatorsFunction()

    install(ContentNegotiation) {
       jsonConfig()
    }

    routing {

        // fjern hvis tjenesten bruker rapids & rivers
        healthApi()

        authenticate {
            route("authenticated") {
                get(){
                    call.respondText("Hello $userIdent!")
                }
            }
        }
    }
}

private fun installAuth(): Application.() -> Unit = {
    installAuthenticators {
        installTokenXAuth {
            setAsDefault = true
        }
    }
}


private val PipelineContext<Unit, ApplicationCall>.userIdent get() = TokenXUserFactory.createTokenXUser(call).ident

fun ContentNegotiationConfig.jsonConfig() = jackson {
    registerModule(JavaTimeModule())
    dateFormat = DateFormat.getDateTimeInstance()
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

}