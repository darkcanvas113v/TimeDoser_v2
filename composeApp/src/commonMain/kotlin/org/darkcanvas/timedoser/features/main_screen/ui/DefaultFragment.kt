package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.isInteractive
import org.darkcanvas.timedoser.features.main_screen.models.TaskUIModel

@Composable
fun DefaultFragment(
  onItemClick: (Int) -> Unit,
  onItemMoved: (Int, Int) -> Unit,
  onItemRemoved: (Int) -> Unit,
  onItemDisabled: (Int) -> Unit,
  tasks: List<TaskUIModel>
) {
  var offsets by remember {
    mutableStateOf<List<Int>>(listOf())
  }

  LaunchedEffect(tasks.size) {
    offsets = List(tasks.size) { 0 }
  }

  if (offsets.size == tasks.size)
  LazyColumn(
    modifier = Modifier.fillMaxHeight()
  ) {
    itemsIndexed(
      items = tasks
    ) { i, item ->
      DraggableItem(
        isDraggable = item.state.isInteractive(),
        offsetPos = offsets[i],
        offsetChanged = { offset ->
          val bottom = tasks.indexOfFirst { it.state.isInteractive() }
          val cappedOffset = capOffset(offset, i, tasks.size, bottom)
          offsets = calculateOffsets(cappedOffset, tasks.size, i)
        },
        onDragCancelled = {
          offsets = List(tasks.size) { 0 }
        },
        onDragFinished = {
          val offset = offsets[i]
          offsets = List(tasks.size) { 0 }
          if (offset != 0) onItemMoved(i, i + offset)
        },
        onSwipedToRight = {
          onItemRemoved(i)
        },
        onSwipedToLeft = {
          onItemDisabled(i)
        }
      ) {
        TaskItem(task = item, onClick = { onItemClick(i) })
      }
    }
  }
}

fun calculateOffsets(offset: Int, size: Int, pos: Int): List<Int> {
  val list = MutableList(size) { 0 }
  list[pos] = offset

  if (offset > 0) {
    for (i in pos + 1 until pos + offset + 1) list[i] = -1
  }
  else {
    for (i in pos + offset until pos) list[i] = 1
  }

  return list
}

fun capOffset(offset: Int, pos: Int, top: Int, bottom: Int = 0): Int {
  return if (pos + offset < bottom) -pos + bottom
  else if (pos + offset >= top) top - pos - 1
  else offset
}
