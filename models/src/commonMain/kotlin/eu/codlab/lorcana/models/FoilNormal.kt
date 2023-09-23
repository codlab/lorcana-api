package eu.codlab.lorcana.models

data class FoilNormal(
    val foil: Long,
    val normal: Long
) {
    fun isOwned(): Boolean {
        return foil > 0 || normal > 0
    }

    fun isNotOwned(): Boolean {
        return !isOwned()
    }
}
