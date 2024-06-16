package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.ui.geometry.Offset
import kotlin.math.abs


data class DragOffset(
  val x: Float = 0f,
  val y: Float = 0f,
  val mode: Mode = when {
    abs(x) > xThreshold -> Mode.Horizontal
    abs(y) > yThreshold -> Mode.Vertical
    else -> Mode.Undefined
  }
) {

  fun translate(offset: Offset): DragOffset {
    val newY = y + offset.y
    val newX = x + offset.x
    val newYAbs = abs(newY)
    val newXAbs = abs(newX)

    return when (mode) {
      Mode.Vertical -> {
        copy(
          y = newY,
          x = 0f
        )
      }

      Mode.Horizontal -> {
        copy(
          x = newX,
          y = 0f
        )
      }

      Mode.Undefined -> {
        if (newXAbs > xThreshold) copy(
          x = newX,
          y = 0f,
          mode = Mode.Horizontal
        )
        else if (newYAbs > yThreshold) copy(
          x = 0f,
          y = newY,
          mode = Mode.Vertical
        )
        else copy(
          x = newX,
          y = newY,
          mode = Mode.Undefined
        )
      }
    }
  }

  enum class Mode {
    Vertical, Horizontal, Undefined
  }

  companion object {
    const val xThreshold = 10
    const val yThreshold = 10

    val DEFAULT = DragOffset()

    val converter = TwoWayConverter<DragOffset, AnimationVector2D>(
      convertToVector = { AnimationVector2D(it.x, it.y) },
      convertFromVector = { DragOffset(it.v1, it.v2) }
    )
  }
}
