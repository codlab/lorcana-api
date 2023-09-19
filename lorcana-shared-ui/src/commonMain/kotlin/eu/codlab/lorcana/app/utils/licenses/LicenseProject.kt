package eu.codlab.lorcana.app.utils.licenses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class LicenseSubLicense(
    val license: String? = "",
    @SerialName("license_url")
    val licenseUrl: String? = ""
)

@Serializable
data class LicenseProject(
    val project: String = "",
    val description: String? = "",
    val version: String? = "",
    val url: String? = "",
    val year: String? = "",
    val licenses: List<LicenseSubLicense> = emptyList(),
    val developers: List<String> = emptyList(),
    val dependency: String = ""
) {
    companion object {
        fun fromContent(content: String): LicenseProject {
            return Json.decodeFromString(content)
        }

        fun fromArray(content: String): List<LicenseProject> {
            return Json.decodeFromString(content)
        }
    }
}
