package eu.codlab.lorcana.models

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema

expect class OwnCardController() {
    fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbFile: String): SqlDriver
}
