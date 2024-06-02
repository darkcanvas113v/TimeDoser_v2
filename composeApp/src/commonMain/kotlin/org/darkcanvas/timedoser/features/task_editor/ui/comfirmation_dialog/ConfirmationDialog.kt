package org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.darkcanvas.timedoser.core.theme.LocalExtendedColors
import org.darkcanvas.timedoser.features.task_editor.TaskEditorComponent

@Composable
fun ConfirmationDialog(
  component: ConfirmationComponent
) {
  Dialog(
    onDismissRequest = component.onDismiss
  ) {
    Surface(
      shape = RoundedCornerShape(4.dp),
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp)
      ) {
        Text(
          text = component.text,
          textAlign = TextAlign.Center
        )

        Spacer(
          modifier = Modifier.height(32.dp)
        )

        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxWidth()
        ) {
          Button(
            onClick = component.onDismiss,
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color.Black.copy(alpha = 0.07f),
              contentColor = Color.Black
            ),
            elevation = null,
            modifier = Modifier.width(90.dp)
          ) {
            Text(text = "No")
          }

          Button(
            onClick = component.onConfirm,
            colors = ButtonDefaults.buttonColors(
              backgroundColor = LocalExtendedColors.current.active,
              contentColor = Color.White
            ),
            modifier = Modifier.width(90.dp)
          ) {
            Text(text = "Yes")
          }
        }
      }
    }
  }
}