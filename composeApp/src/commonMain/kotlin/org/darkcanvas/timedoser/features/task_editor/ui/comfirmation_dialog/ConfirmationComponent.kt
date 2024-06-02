package org.darkcanvas.timedoser.features.task_editor.ui.comfirmation_dialog

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

class ConfirmationComponent(
  taskName: String,
  val pos: Int,
  val onDismiss: () -> Unit,
  val onConfirm: () -> Unit
) {
  val text: AnnotatedString = buildAnnotatedString {
    append("Do you really want to delete ")
    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
      append(taskName)
    }
    append("?")
  }
}