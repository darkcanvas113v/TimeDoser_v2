package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.zIndex


@Composable
fun DraggableItem(
  isDraggable: Boolean,
  offsetChanged: (Int) -> Unit,
  onDragCancelled: () -> Unit,
  onDragFinished: (Int) -> Unit,
  offset: Int,
  content: @Composable () -> Unit,
) {
  var zIndex by remember { mutableStateOf(0f) }
  var offsetY by remember { mutableStateOf(0f) }
  var size by remember() { mutableStateOf(IntSize.Zero) }

  LaunchedEffect(offsetY) {
    val currentOffset = (offsetY / size.height).toInt()
    if (offset != currentOffset)
      offsetChanged(currentOffset)
  }

  if (isDraggable)
    Box(
      modifier = Modifier
        .onGloballyPositioned { size = it.size }
        .graphicsLayer {
          translationY = if (offsetY != 0f) offsetY else size.height * offset.toFloat()
        }
        .zIndex(zIndex)
        .pointerInput(Unit) {
            detectDragGesturesAfterLongPress(
              onDrag = { change, dragAmount ->
                change.consume()
                offsetY += dragAmount.y
              },
              onDragStart = {
                zIndex = 2f
              },
              onDragEnd = {
                offsetY = 0f
                zIndex = 1f
                onDragFinished(offset)
              },
              onDragCancel = {
                offsetY = 0f
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