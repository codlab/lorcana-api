package eu.codlab.lorcana.models

class LorcanaController {
    private val ownCardController = OwnCardController()

    private var database: Database? = null

    private var cardMap: MutableMap<Pair<String, Number>, OwnCard>? = null

    init {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        try {
            val driver = ownCardController.createDriver(Database.Schema, "database.db")
            database = Database(driver)
        } catch (e: NullPointerException) {
            // nothing
        }
    }

    fun selectAll(): Map<Pair<String, Number>, OwnCard> {
        if (null == cardMap) {
            var mutable: MutableMap<Pair<String, Number>, OwnCard> = mutableMapOf()
            val list = database?.ownedCardQueries?.selectAll()?.executeAsList() ?: emptyList()

            list.forEach {
                mutable[Pair(it.setCode, it.cardNumber)] = it
            }

            cardMap = mutable
        }
        return cardMap ?: emptyMap()
    }

    fun exists(setCode: String, cardNumber: Long): Boolean {
        return selectAll().containsKey(Pair(setCode, cardNumber))
    }

    fun get(setCode: String, cardNumber: Long): FoilNormal {
        val ownCard = cardMap?.get(Pair(setCode, cardNumber))

        return FoilNormal(
            foil = ownCard?.foil ?: 0,
            normal = ownCard?.normal ?: 0
        )
    }

    fun save(setCode: String, cardNumber: Long, numbers: FoilNormal) {
        val toFind = Pair(setCode, cardNumber)
        val ownCard = cardMap?.get(toFind)

        if (null != ownCard) {
            val newOwnCard = ownCard.copy(
                foil = numbers.foil,
                normal = numbers.normal
            )
            cardMap?.set(toFind, newOwnCard)
            save(newOwnCard)
        } else {
            val newOwnCard = OwnCard(
                setCode = setCode,
                cardNumber = cardNumber,
                foil = numbers.foil,
                normal = numbers.normal
            )
            cardMap?.set(toFind, newOwnCard)
            insert(newOwnCard)
        }
    }

    private fun save(ownCard: OwnCard) {
        database?.ownedCardQueries?.update(
            setCode = ownCard.setCode,
            cardNumber = ownCard.cardNumber,
            foil = ownCard.foil,
            normal = ownCard.normal
        )
    }

    private fun insert(ownCard: OwnCard) {
        database?.ownedCardQueries?.insert(
            setCode = ownCard.setCode,
            cardNumber = ownCard.cardNumber,
            foil = ownCard.foil,
            normal = ownCard.normal
        )
    }
}
