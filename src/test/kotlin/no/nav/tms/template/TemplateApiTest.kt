package no.nav.tms.template;

import io.ktor.client.request.get
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import no.nav.tms.token.support.authentication.installer.mock.installMockedAuthenticators
import no.nav.tms.token.support.tokenx.validation.mock.SecurityLevel
import org.junit.jupiter.api.Test


class TemplateApiTest {

    @Test
    fun `eksempel på testing av autentisert rute`() = testApplication {
        application {
            templateApi()
        }
        client.get("/authenticated").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun `eksempel på testing av uautentisert rute`() = testApplication {
        application {
            templateApi()
        }
        client.get("/internal/isAlive").apply {
            TODO("Please write your test here")
        }
    }
}

/*
fun ApplicationTestBuilder.mockedAuth() = installMockedAuthenticators {
    installTokenXAuthMock {
        alwaysAuthenticated = true
        setAsDefault = true
        staticUserPid = testFnr2
        staticSecurityLevel = SecurityLevel.LEVEL_4
    }
}*/