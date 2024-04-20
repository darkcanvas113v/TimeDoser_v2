package org.darkcanvas.timedoser.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun DefaultTheme(content: @Composable () -> Unit) {
  CompositionLocalProvider(
    LocalExtendedColors provides ExtendedColors()
  ) {
    MaterialTheme(
      colors = LightColorPalette,
      typography = Type,
      shapes = shapes,
      content = content
    )
  }
}