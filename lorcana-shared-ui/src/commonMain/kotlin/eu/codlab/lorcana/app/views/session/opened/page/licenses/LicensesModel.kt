package eu.codlab.lorcana.app.views.session.opened.page.licenses

import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.app.utils.licenses.LicenseProject
import eu.codlab.lorcana.resources.Resources
import eu.codlab.moko.ext.safelyReadContent

data class LicenseState(
    var loaded: Boolean = false,
    var licenses: List<LicenseProject> = emptyList()
)

class LicensesModel(
    loaded: Boolean = false,
    licenses: List<LicenseProject> = emptyList()
) : StateViewModel<LicenseState>(LicenseState(loaded = loaded, licenses = licenses)) {

    companion object {
        fun fake(
            loaded: Boolean = false,
            licenses: List<LicenseProject> = emptyList()
        ): LicensesModel {
            return LicensesModel(loaded, licenses)
        }
    }

    fun load() = launch {
        val content = Resources.files.licenseReleaseReport.safelyReadContent()
        println(content)
        val result = LicenseProject.fromArray(content)

        println(result)
        updateState {
            copy(loaded = true, licenses = result)
        }
    }
}
