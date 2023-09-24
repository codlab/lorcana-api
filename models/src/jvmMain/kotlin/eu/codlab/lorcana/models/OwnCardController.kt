package eu.codlab.lorcana.models

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class OwnCardController {
    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbFile: String): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:test.db")
        schema.create(driver)
        return driver
    }
}
