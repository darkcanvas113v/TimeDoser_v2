package org.darkcanvas.timedoser.previews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Day
import org.darkcanvas.timedoser.features.main_screen.ui.PlayerControls
import org.kodein.di.weakReference

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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Preview
@Composable
fun TestPreview() {
  var visible by remember { mutableStateOf(false) }
  Box(modifier = Modifier.systemBarsPadding()) {
    LazyColumn(modifier = Modifier.fillMaxSize().imePadding().imeNestedScroll()) {}

    TextField("", {})

    Button(
      onClick = { visible = true },
      modifier = Modifier.align(Alignment.BottomCenter).imePadding()
    ) {

    }

    if (visible) {
      Dialog(onDismissRequest = { visible = false }) {
        Surface {
          TextField("", {})
        }
      }
    }


  }
}