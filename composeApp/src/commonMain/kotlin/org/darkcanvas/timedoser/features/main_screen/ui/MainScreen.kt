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
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.main_screen.MainScreenComponent

@Composable
fun MainScreen(
  component: MainScreenComponent,
  onAddButtonClick: () -> Unit,
  onItemClick: (Int) -> Unit
) {
  val dayState by remember(component) {
    component.dayState
  }.collectAsState(initial = Day.DEFAULT)

  Surface {
    Column(modifier = Modifier.fillMaxSize()) {
      Box(modifier = Modifier.weight(1f)) {
          DefaultFragment(
            onItemClick = onItemClick,
            tasks = dayState.items
          )
      }

      PlayerControls(
        onAddButtonClick = onAddButtonClick,
        onPauseButtonClick = { component.pause() },
        onPlayButtonClick = { component.play() },
        onStopButtonClick = { component.stop() },
        dayState = dayState.state
      )
    }
  }
}