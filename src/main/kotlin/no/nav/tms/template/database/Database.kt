package no.nav.tms.template.database

import com.zaxxer.hikari.HikariDataSource
import kotliquery.Query
import kotliquery.action.ListResultQueryAction
import kotliquery.action.NullableResultQueryAction

import kotliquery.sessionOf
import kotliquery.using
import no.nav.tms.template.config.Environment
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.FluentConfiguration

interface Database {
    val dataSource: HikariDataSource
    fun update(queryBuilder: () -> Query) {
        using(sessionOf(dataSource)) {
            it.run(queryBuilder.invoke().asUpdate)
        }
    }

    fun <T> query(action: () -> NullableResultQueryAction<T>): T? =
        using(sessionOf(dataSource)) {
            it.run(action.invoke())
        }

    fun <T> list(action: () -> ListResultQueryAction<T>): List<T> =
        using(sessionOf(dataSource)) {
            it.run(action.invoke())
        }

}

object Flyway {

    fun runFlywayMigrations(env: Environment) {
        val flyway = configure(env).load()
        flyway.migrate()
    }

    private fun configure(env: Environment): FluentConfiguration {
        val configBuilder = Flyway.configure().connectRetries(5)
        val dataSource = createDataSourceForLocalDbWithUser(env)
        configBuilder.dataSource(dataSource)

        return configBuilder
    }

    private fun createDataSourceForLocalDbWithUser(env: Environment): HikariDataSource {
        return PostgresDatabase.hikariFromLocalDb(env)
    }

}
