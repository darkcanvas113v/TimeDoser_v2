import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.darkcanvas.timedoser.app.App
import org.darkcanvas.timedoser.app.AppDIContainer
import org.darkcanvas.timedoser.app.DefaultRootComponent
import org.darkcanvas.timedoser.runOnUiThread
import javax.sound.sampled.AudioSystem

@OptIn(ExperimentalDecomposeApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()

    val root = runOnUiThread { DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle),
        container = AppDIContainer()
    ) }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "TimeDoser"
        ) {
            MaterialTheme {
                App(root)
            }
        }
    }
}
