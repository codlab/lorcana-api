package eu.codlab.lorcana.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.interop.LocalUIViewController
import com.moriatsushi.insetsx.WindowInsetsUIViewController
import eu.codlab.lorcana.app.views.home.App
import eu.codlab.lorcana.app.views.widgets.ProvideSafeArea
import platform.UIKit.UIApplication
import platform.UIKit.UIScene
import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.UIWindowSceneDelegateProtocol
import platform.UIKit.currentTraitCollection

@Composable
fun isSystemInDarkTheme(): Boolean {
    var style: UIUserInterfaceStyle by remember {
        mutableStateOf(UITraitCollection.currentTraitCollection.userInterfaceStyle)
    }

    val viewController: UIViewController = LocalUIViewController.current
    DisposableEffect(Unit) {
        val view: UIView = viewController.view
        val traitView = TraitView {
            style = UITraitCollection.currentTraitCollection.userInterfaceStyle
        }
        view.addSubview(traitView)

        onDispose {
            traitView.removeFromSuperview()
        }
    }

    return style == UIUserInterfaceStyle.UIUserInterfaceStyleDark
}

@Suppress("FunctionNaming")
fun MainViewController() = WindowInsetsUIViewController {
    val isSystemDarkTheme = isSystemInDarkTheme()

    println("isSystemDarkTheme $isSystemDarkTheme")

    UIApplication.sharedApplication.connectedScenes.first()?.let {
        val scene = it as UIScene
        val windowSceneDelegate = scene.delegate as UIWindowSceneDelegateProtocol

        ProvideSafeArea(
            window = windowSceneDelegate.window!!
        ) {
            App(
                isDarkTheme = isSystemDarkTheme
            )
        }
    }
}