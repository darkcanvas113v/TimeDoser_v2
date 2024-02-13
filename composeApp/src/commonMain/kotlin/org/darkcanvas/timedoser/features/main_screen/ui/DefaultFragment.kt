package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

@Composable
fun DefaultFragment(
  onItemClick: (Int) -> Unit,
  tasks: List<Task>
) {
  LazyColumn {
    items(
      items = tasks
    ) {
      TaskItem(task = it)
    }
  }
}