package eu.codlab.lorcana.models

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class OwnCardController {
    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbFile: String): SqlDriver {
        return AndroidSqliteDriver(schema, AndroidContext!!, dbFile)
    }
}