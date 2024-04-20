package org.darkcanvas.timedoser

import org.darkcanvas.timedoser.app.App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import org.darkcanvas.timedoser.app.DefaultRootComponent
import org.darkcanvas.timedoser.previews.TestPreview

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val root = DefaultRootComponent(
      componentContext = defaultComponentContext(),
      container = (application as App).container
    )

    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      App(component = root)
    }
  }
}
