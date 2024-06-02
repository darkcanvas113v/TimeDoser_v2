package org.darkcanvas.timedoser.core.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFF58A3E8)
val Purple500 = Color(0xFF58A3E8)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFFF0903C)

val Gray = Color(0xFF4A6066)
val DarkRed = Color(0xFFD1411E)

val LightColorPalette = lightColors(
  primary = Purple500,
  primaryVariant = Purple200,
  secondary = Teal200,
  onPrimary = Color.White,
  onSecondary = Color.White
)

data class ExtendedColors(
  val disabled: Color = Gray,
  val active: Color = DarkRed,
  val completed: Color = Purple700
)

val LocalExtendedColors = staticCompositionLocalOf {
  ExtendedColors()
}

