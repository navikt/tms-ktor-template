package no.nav.tms.template.health

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import kotlinx.coroutines.coroutineScope
import kotlinx.html.*
import no.nav.tms.template.config.Environment
import no.nav.tms.template.config.HttpClientBuilder

suspend fun ApplicationCall.buildSelftestPage(healthService: HealthService) = coroutineScope {

    val healthChecks = healthService.getHealthChecks()
    val hasFailedChecks = healthChecks.any { healthStatus -> Status.ERROR == healthStatus.status }

    respondHtml(status =
    if(hasFailedChecks) {
        HttpStatusCode.ServiceUnavailable
    } else {
        HttpStatusCode.OK
    })
    {
        head {
            title { +"Selftest tms-ktor-template" }
        }
        body {
            var text = if(hasFailedChecks) {
                "FEIL"
            } else {
                "Service-status: OK"
            }
            h1 {
                style = if(hasFailedChecks) {
                    "background: red;font-weight:bold"
                } else {
                    "background: green"
                }
                +text
            }
            table {
                thead {
                    tr { th { +"SELFTEST tms-ktor-template" } }
                }
                tbody {
                    healthChecks.map {
                        tr {
                            td { +it.serviceName }
                            td {
                                style = if(it.status == Status.OK) {
                                    "background: green"
                                } else {
                                    "background: red;font-weight:bold"
                                }
                                +it.status.toString()
                            }
                            td { +it.statusMessage }
                        }
                    }
                }
            }
        }
    }
}
