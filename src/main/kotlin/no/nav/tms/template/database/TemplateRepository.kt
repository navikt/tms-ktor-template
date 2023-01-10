package no.nav.tms.template.database

import kotliquery.queryOf


class TemplateRepository(private val database: Database) {

    fun getExample(ident: String): String? = database.query {
        queryOf("select example_field from example_table where ident=:ident", mapOf("ident" to ident)).map { row ->
            row.string("template_field")
        }.asSingle
    }


    private fun updateExample(ident: String) {
        database.update {
            queryOf(
                """INSERT INTO example_table (ident, example_field) VALUES (:ident, :example_value) 
                    |ON CONFLICT DO NOTHING
                """.trimMargin(),
                mapOf(
                    "ident" to ident,
                    "example_value" to "examples are useful"
                )
            )
        }
    }
}