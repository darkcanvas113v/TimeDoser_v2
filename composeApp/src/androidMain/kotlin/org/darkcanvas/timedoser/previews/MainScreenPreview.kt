package org.darkcanvas.timedoser.previews

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.main_screen.ui.PlayerControls

@Preview
@Composable
fun MainScreenPreview() {
  Surface {
    PlayerControls(
      onAddButtonClick = {},
      onPauseButtonClick = {},
      onPlayButtonClick = {},
      onStopButtonClick = {},
      dayState = Day.State.WAITING
    )
  }
}