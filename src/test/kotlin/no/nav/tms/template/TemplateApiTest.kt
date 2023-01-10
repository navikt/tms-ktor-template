package no.nav.tms.template;

import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication
import no.nav.tms.token.support.authentication.installer.mock.installMockedAuthenticators
import no.nav.tms.token.support.tokenx.validation.mock.SecurityLevel
import org.junit.jupiter.api.Test


class TemplateApiTest {

    @Test
    fun `eksempel på testing av autentisert rute`() = testApplication {
        application {
            templateApi { mockedAuth() }
        }
        client.get("/authenticated").assert {
            status shouldBe HttpStatusCode.OK
            bodyAsText() shouldBe "Hello 123456788910!"
        }
    }

    @Test
    fun `eksempel på testing av uautentisert rute`() = testApplication {
        application {
            templateApi { mockedAuth() }
        }
        client.get("/internal/isAlive").apply {
            status shouldBe HttpStatusCode.OK
        }
    }
}


fun Application.mockedAuth(testIdent:String = "123456788910") = installMockedAuthenticators {
    installTokenXAuthMock {
        alwaysAuthenticated = true
        setAsDefault = true
        staticUserPid = testIdent
        staticSecurityLevel = SecurityLevel.LEVEL_4
    }
}