package eu.codlab.lorcana.app.views.session.opened.page.licenses

import com.github.codlab.lorcana.files.readContent
import com.github.codlab.lorcana.sharedui.Resources
import eu.codlab.lorcana.app.utils.StateViewModel
import eu.codlab.lorcana.app.utils.launch
import eu.codlab.lorcana.app.utils.licenses.LicenseProject

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
        val content = Resources.files.licenseReleaseReport.readContent()
        println(content)
        val result = LicenseProject.fromArray(content)

        println(result)
        updateState {
            copy(loaded = true, licenses = result)
        }
    }
}
