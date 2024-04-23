package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task
import org.darkcanvas.timedoser.features.main_screen.models.TaskUIModel

@Composable
fun DefaultFragment(
  onItemClick: (Int) -> Unit,
  tasks: List<TaskUIModel>
) {
  LazyColumn {
    itemsIndexed(
      items = tasks
    ) { i, item ->
      TaskItem(task = item, onClick = { onItemClick(i) })
    }
  }
}