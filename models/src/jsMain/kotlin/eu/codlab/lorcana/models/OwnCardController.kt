package eu.codlab.lorcana.models

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker

actual class OwnCardController {

    actual fun createDriver(schema: SqlSchema<QueryResult.Value<Unit>>, dbFile: String): SqlDriver {
        return WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        )
    }
}