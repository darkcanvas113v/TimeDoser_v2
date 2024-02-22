package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day

@Composable
fun PlayerControls(
  dayState: Day.State,
  onPlayButtonClick: () -> Unit,
  onStopButtonClick: () -> Unit,
  onAddButtonClick: () -> Unit,
  onPauseButtonClick: () -> Unit
) {

  Row(
    horizontalArrangement = Arrangement.SpaceAround,
    modifier = Modifier.fillMaxWidth()
  ) {
    IconButton(onClick = onStopButtonClick) {
      Icon(
        imageVector = Icons.Filled.Stop,
        contentDescription = null,
        modifier = Modifier.size(32.dp)
      )
    }

    when (dayState) {
      Day.State.WAITING, Day.State.DISABLED, Day.State.COMPLETED -> {
        IconButton(onClick = onPlayButtonClick) {
          Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
          )
        }
      }
      else -> {
        IconButton(onClick = onPauseButtonClick) {
          Icon(
            imageVector = Icons.Filled.Pause,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }

    IconButton(onClick = onAddButtonClick) {
      Icon(
        imageVector = Icons.Filled.Add,
        contentDescription = null,
        modifier = Modifier.size(32.dp)
      )
    }
  }
}