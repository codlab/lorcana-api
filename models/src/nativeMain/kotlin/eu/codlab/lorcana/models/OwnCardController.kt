package eu.codlab.lorcana.models

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class OwnCardController {
    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbFile: String): SqlDriver {
        return NativeSqliteDriver(schema, dbFile)
    }
}
