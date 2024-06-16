package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

  val draggableState = remember() {
    DragState(
      lazyListState = state,
      scope = scope,
      onItemMoved = onItemMoved
    )
  }

  LaunchedEffect(tasks) {
    draggableState.firstInteractable = tasks.indexOfFirst { it.state.isInteractive() }
  }



  LazyColumn(
    state = state,
    modifier = Modifier
      .fillMaxHeight()
      .pointerInput(draggableState.dragCancelMagicNumber) {
        detectDragGesturesAfterLongPress(
          onDrag = { change, offset ->
            change.consume()
            draggableState.onDrag(offset)
          },
          onDragStart = { offset ->
            draggableState.onDragStart(offset)
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
  var offsetAbsolute by mutableStateOf(0f)
  val prevOffset = Animatable(DragOffset.DEFAULT, DragOffset.converter)
  var dragCancelMagicNumber by mutableStateOf(0)

  var firstInteractable by mutableStateOf(0)

  var overscrollJob by mutableStateOf<Job?>(null)

  fun finishDrag() {
    overscrollJob?.cancel()

    offsetAbsolute = 0f
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

  private fun getIndexForOffset(offset: Offset): Int? {
    return lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { item -> offset.y.toInt() in item.offset..item.offset + item.size }
      ?.index
  }

  fun onDragStart(offset: Offset) {
    val index = getIndexForOffset(offset)
    if (index == null || index < firstInteractable) {
      return cancelDrag()
    }

    lazyListState.layoutInfo.visibleItemsInfo.first { it.index == index }.let {
      currentIndex = index
      itemInfo = it
    }
    overscrollJob = scope.launch {
      while (true) {
        delay(200)
        checkForOverScroll()?.let { overscrollAmount ->
          val scroll = lazyListState.scrollBy(overscrollAmount)
          translate(Offset(0f, scroll))
        }
      }
    }
  }

  private fun translate(offset: Offset) {
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
  }

  fun onDrag(offset: Offset) {
    offsetAbsolute += offset.y
    translate(offset)

    if (abs(currentOffset.x) > 600) {
      cancelDrag()
    }
  }

  fun checkForOverScroll(): Float? {
    return itemInfo?.let {
      val startOffset = it.offset.toFloat() + offsetAbsolute
      val endOffset = it.offset.toFloat() + it.size + offsetAbsolute
      val viewPortEnd = lazyListState.layoutInfo.viewportEndOffset
      val viewPortStart = lazyListState.layoutInfo.viewportStartOffset

      println("StartOffset is $startOffset, endOffset is $endOffset, viewPortStart is $viewPortStart viewPortEnd is $viewPortEnd")

      when {
        currentOffset.y > 0 -> (endOffset - viewPortEnd).takeIf { diff -> diff > 0 }
        currentOffset.y < 0 -> (startOffset - viewPortStart).takeIf { diff -> diff < 0 }
        else -> null
      }
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
