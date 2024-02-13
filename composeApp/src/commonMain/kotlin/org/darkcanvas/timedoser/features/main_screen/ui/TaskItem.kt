package org.darkcanvas.timedoser.features.main_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

@Composable
fun TaskItem(
  task: Task
) {
  Surface(
    elevation = 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 8.dp, end = 8.dp, top = 16.dp)
  ) {
    Row(
      modifier = Modifier
        .height(100.dp)
    ) {
      Box(
        modifier = Modifier
          .background(MaterialTheme.colors.primary)
          .fillMaxHeight()
          .width(56.dp)
      ) {
        Box(
          modifier = Modifier
            .padding(top = 8.dp)
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.onPrimary)
            .align(Alignment.TopCenter)
        ) {
          Icon(
            imageVector = Icons.Filled.Radio,
            contentDescription = "",
            modifier = Modifier
              .fillMaxSize()
              .padding(4.dp)
          )
        }

        Text(
          text = task.startTime.toString(),
          style = MaterialTheme.typography.subtitle1,
          color = MaterialTheme.colors.onPrimary,
          modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(top = 16.dp, bottom = 8.dp)
            .padding(horizontal = 4.dp)
        )
      }

      Column(
        modifier = Modifier
          .padding(12.dp)
          .fillMaxHeight(),
      ) {
        Text(
          text = task.name,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = task.progress.toString(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterStart)
          )
          Text(
            text = (task.duration - task.progress).toString(),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterEnd)
          )
        }

        LinearProgressIndicator(
          progress = (task.progress.toFloat() / task.duration),
          color = MaterialTheme.colors.primary.copy(alpha = 0.7f),
          modifier = Modifier
            .padding(top = 2.dp)
            .fillMaxWidth()
        )
      }
    }
  }
}