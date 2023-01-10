package no.nav.tms.template

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import no.nav.helse.rapids_rivers.RapidApplication
import no.nav.helse.rapids_rivers.RapidsConnection
import no.nav.tms.template.config.Environment
import no.nav.tms.template.database.Flyway
import no.nav.tms.template.rapids.ExampleSink

fun main(){
    startRapid(Environment())
}



private fun startRapid(
    environment: Environment,
) {
    RapidApplication.Builder(RapidApplication.RapidApplicationConfig.fromEnv(environment.rapidConfig())).withKtorModule {
        //api oppsett, fjern hvis tjenesten kun bruker R&R
        templateApi()
    }.build().apply {
        //rapidsoppsett
        ExampleSink(this)

        register(object : RapidsConnection.StatusListener {
            override fun onStartup(rapidsConnection: RapidsConnection) {
                Flyway.runFlywayMigrations(environment)
            }
        })
    }
}

// Bruk denne hvis tjenesten kun tilbyr ett api
private fun startApi(){
    embeddedServer(Netty, port = 8080) {
        templateApi()
    }.start(wait = true)
}