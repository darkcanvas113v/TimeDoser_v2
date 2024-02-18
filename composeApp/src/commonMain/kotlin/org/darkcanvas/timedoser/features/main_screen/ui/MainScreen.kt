package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent
import org.darkcanvas.timedoser.features.main_screen.models.DayUIModel
import org.darkcanvas.timedoser.features.task_editor.ui.TaskEditor

@Composable
fun MainScreen(
  component: MainScreenComponent,
) {
  val dayState by remember(component) {
    component.dayState
  }.collectAsState(initial = DayUIModel.INITIAL)

  val taskEditorDialog by component.taskEditorComponent.subscribeAsState()

  Surface {
    Column(modifier = Modifier.fillMaxSize()) {
      Box(modifier = Modifier.weight(1f)) {
        DefaultFragment(
          onItemClick = component::editTask,
          tasks = dayState.items
        )
      }

      PlayerControls(
        onAddButtonClick = component::addTask,
        onPauseButtonClick = component::pause,
        onPlayButtonClick = component::play,
        onStopButtonClick = component::stop,
        dayState = dayState.state
      )
    }

    taskEditorDialog.child?.instance?.also {
      TaskEditor(component = it)
    }
  }
}