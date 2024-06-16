package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.zIndex
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.isInteractive
import org.darkcanvas.timedoser.features.main_screen.models.TaskUIModel
import kotlin.math.abs

@Composable
fun DefaultFragment(
  onItemClick: (Int) -> Unit,
  onItemMoved: (Int, Int) -> Unit,
  onItemRemoved: (Int) -> Unit,
  onItemDisabled: (Int) -> Unit,
  tasks: List<TaskUIModel>
) {

  val state = rememberLazyListState()
  val scope = rememberCoroutineScope()
  var firstInteractable by remember { mutableStateOf(0) }

  LaunchedEffect(tasks) {
    firstInteractable = tasks.indexOfFirst { it.state.isInteractive() }
  }

  val draggableState = remember() {
    DragState(
      lazyListState = state,
      scope = scope,
      onItemMoved = onItemMoved
    )
  }

  LazyColumn(
    state = state,
    modifier = Modifier
      .fillMaxHeight()
      .pointerInput(draggableState.dragCancelMagicNumber) {
        detectDragGesturesAfterLongPress(
          onDrag = { change, offset ->
            change.consume()
            draggableState.onDrag(offset, firstInteractable)
          },
          onDragStart = { offset ->

            val index = draggableState.getIndexForOffset(offset)
            if (index != null && index >= firstInteractable) {
              draggableState.onDragStart(index)
            } else {
              draggableState.cancelDrag()
            }
          },
          onDragEnd = draggableState::finishDrag,
          onDragCancel = {
            if (draggableState.currentOffset.x < -600)
              onItemDisabled(draggableState.currentIndex)
            else if (draggableState.currentOffset.x > 600)
              onItemRemoved(draggableState.currentIndex)
            draggableState.finishDrag()
          }
        )
      }
  ) {
    itemsIndexed(
      items = tasks
    ) { i, item ->
      val itemState by remember { derivedStateOf {draggableState.resolveDraggableState(i)} }

      Box(
        modifier = Modifier.graphicsLayer {
          translationY = itemState.y
          translationX = itemState.x
        }.zIndex(
          itemState.z
        )
      ) {
        TaskItem(task = item, onClick = { onItemClick(i) })
      }
    }
  }
}

class DragState(
  val lazyListState: LazyListState,
  val scope: CoroutineScope,
  val onItemMoved: (Int, Int) -> Unit
) {
  var itemInfo by mutableStateOf<LazyListItemInfo?>(null)
  var currentIndex by mutableStateOf(0)
  var prevIndex by mutableStateOf(-1)
  var currentOffset by mutableStateOf(DragOffset(0f, 0f))
  val prevOffset = Animatable(DragOffset.DEFAULT, DragOffset.converter)
  var dragCancelMagicNumber by mutableStateOf(0)

  fun finishDrag() {
    prevIndex = currentIndex
    currentIndex = -1
    scope.launch {
      prevOffset.snapTo(currentOffset)
      currentOffset = DragOffset.DEFAULT
      prevOffset.animateTo(DragOffset.DEFAULT)
    }
  }

  fun cancelDrag() {
    dragCancelMagicNumber++
  }

  fun resolveDraggableState(index: Int) = when (index) {
    currentIndex -> {
      DraggableItemState(
        y = currentOffset.y,
        x = currentOffset.x,
        z = 3f
      )
    }

    prevIndex -> {
      DraggableItemState(
        y = prevOffset.value.y,
        x = prevOffset.value.x,
        z = 2f
      )
    }

    else -> DraggableItemState()
  }

  fun getIndexForOffset(offset: Offset): Int? {
    return lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { item -> offset.y.toInt() in item.offset..item.offset + item.size }
      ?.index
  }

  fun onDragStart(index: Int) {
    lazyListState.layoutInfo.visibleItemsInfo[index].let {
      currentIndex = index
      itemInfo = it
    }
  }

  fun onDrag(offset: Offset, firstInteractable: Int) {
    currentOffset = currentOffset.translate(offset)

    itemInfo?.let { info ->
      val itemOffset = capOffset(
        (currentOffset.y / info.size).toInt(),
        currentIndex,
        lazyListState.layoutInfo.totalItemsCount,
        firstInteractable
      )
      if (abs(itemOffset) > 0) {
        onItemMoved(currentIndex, currentIndex + itemOffset)

        scope.launch {
          prevOffset.snapTo(DragOffset(currentOffset.x, itemOffset.toFloat() * info.size))
          prevIndex = currentIndex

          currentIndex += itemOffset
          currentOffset =
            currentOffset.translate(Offset(0f, -itemOffset * info.size.toFloat()))

          prevOffset.animateTo(DragOffset.DEFAULT)

        }
      }
    }

    if (abs(currentOffset.x) > 600) {
      dragCancelMagicNumber++
    }
  }
}

data class DraggableItemState(
  val x: Float = 0f,
  val y: Float = 0f,
  val z: Float = 1f,
)

fun calculateOffsets(offset: Int, size: Int, pos: Int): List<Int> {
  val list = MutableList(size) { 0 }
  list[pos] = offset

  if (offset > 0) {
    for (i in pos + 1 until pos + offset + 1) list[i] = -1
  } else {
    for (i in pos + offset until pos) list[i] = 1
  }

  return list
}

fun capOffset(offset: Int, pos: Int, top: Int, bottom: Int = 0): Int {
  return if (pos + offset < bottom) -pos + bottom
  else if (pos + offset >= top) top - pos - 1
  else offset
}
