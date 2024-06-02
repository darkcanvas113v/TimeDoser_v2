package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlin.math.abs


private data class DragOffset(
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
    var newMode = mode

    if (mode == Mode.Vertical && newYAbs < yThreshold) newMode = Mode.Undefined
    else if (mode == Mode.Horizontal && newXAbs < xThreshold) newMode = Mode.Undefined

    return when (newMode) {
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


@Composable
fun DraggableItem(
  isDraggable: Boolean,
  offsetChanged: (Int) -> Unit,
  onDragCancelled: () -> Unit,
  onDragFinished: (Int) -> Unit,
  onSwipedToLeft: () -> Unit,
  onSwipedToRight: () -> Unit,
  offsetPos: Int,
  content: @Composable () -> Unit,
) {
  var zIndex by remember { mutableStateOf(0f) }
  var size by remember() { mutableStateOf(IntSize.Zero) }
  val offsetAnim = remember { Animatable(DragOffset.DEFAULT, DragOffset.converter) }
  Offset.VectorConverter
  var dragCancelMagicNumber by remember { mutableStateOf(0) }

  val scope = rememberCoroutineScope()


  LaunchedEffect(offsetAnim.value.y) {
    if (offsetAnim.isRunning) return@LaunchedEffect

    val currentOffset = (offsetAnim.value.y / size.height).toInt()
    if (offsetPos != currentOffset)
      offsetChanged(currentOffset)
  }

  if (isDraggable)
    Box(
      modifier = Modifier
        .onGloballyPositioned { size = it.size }
        .graphicsLayer {
          translationX = offsetAnim.value.x
          translationY = if (offsetAnim.value.y != 0f) offsetAnim.value.y else size.height * offsetPos.toFloat()
        }
        .zIndex(zIndex)
        .pointerInput(dragCancelMagicNumber) {
            detectDragGesturesAfterLongPress(
              onDrag = { change, dragAmount ->
                change.consume()
                scope.launch { offsetAnim.snapTo(offsetAnim.value.translate(dragAmount)) }

                if (abs(offsetAnim.value.x) > 600) {
                  dragCancelMagicNumber++
                }
              },
              onDragStart = {
                zIndex = 2f
              },
              onDragEnd = {
                scope.launch { offsetAnim.animateTo(DragOffset.DEFAULT) }
                zIndex = 1f
                onDragFinished(offsetPos)
              },
              onDragCancel = {
                if (offsetAnim.value.x < -600)
                  onSwipedToLeft()
                else if (offsetAnim.value.x > 600)
                  onSwipedToRight()

                scope.launch { offsetAnim.animateTo(DragOffset.DEFAULT) }
                zIndex = 1f
                onDragCancelled()
              }
            )
        }
    ) {
      content()
    }
  else content()
}