package eu.codlab.lorcana.app.views.session.opened.page.licenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import eu.codlab.lorcana.app.theme.MyApplicationTheme
import eu.codlab.lorcana.app.utils.licenses.LicenseProject
import eu.codlab.lorcana.app.utils.licenses.LicenseSubLicense
import eu.codlab.lorcana.app.views.widgets.TextNormal
import eu.codlab.lorcana.app.views.widgets.TextSubtitle
import eu.codlab.lorcana.app.views.widgets.systemBackground

@Composable
fun LicenseItem(license: LicenseProject) {
    val licenses = license.licenses.map { it.license }.filter {
        !it.isNullOrEmpty()
    }.joinToString(separator = ", ")

    Column(modifier = Modifier.fillMaxWidth()) {
        TextSubtitle(
            text = license.project
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f)
        ) {
            TextNormal(text = "version ${license.version ?: "-"}")
            TextNormal(text = licenses)
            TextNormal(text = license.description ?: "", textAlign = TextAlign.Justify)
        }
    }
}

internal val fakeLicense = LicenseProject(
    project = "test",
    version = "10",
    licenses = listOf(
        LicenseSubLicense(
            license = "MIT"
        ),
        LicenseSubLicense(
            license = "Apache"
        )
    ),
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
)

@Preview
@Composable
fun LicenseItemPreviewDark() {
    MyApplicationTheme(darkTheme = true) {
        Column(modifier = Modifier.systemBackground()) {
            LicenseItem(
                license = fakeLicense
            )
        }
    }
}

@Preview
@Composable
fun LicenseItemPreviewLight() {
    MyApplicationTheme(darkTheme = false) {
        Column(modifier = Modifier.systemBackground()) {
            LicenseItem(
                license = fakeLicense
            )
        }
    }
}
