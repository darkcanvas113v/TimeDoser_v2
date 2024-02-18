package org.darkcanvas.timedoser.features.task_editor.ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.darkcanvas.timedoser.core.util.convertToMillis
import org.darkcanvas.timedoser.core.util.formatValue
import org.darkcanvas.timedoser.core.util.getHoursAndMinutes

@Composable
fun TimePickerDialog(
  visible: Boolean,
  onDismiss: () -> Unit,
  time: Long,
  onGetResult: (Long) -> Unit
) {

  val (hours, minutes) = getHoursAndMinutes(time)

  val (mHours, setHours) = remember {
    mutableStateOf(hours)
  }

  val (mMinutes, setMinutes) = remember {
    mutableStateOf(minutes)
  }

  if (visible) {
    Dialog(
      onDismissRequest = onDismiss
    ) {
      Surface(
        shape = MaterialTheme.shapes.small
      ) {
        Column(
          modifier = Modifier
            .padding(vertical = 16.dp)
            .padding(horizontal = 16.dp)
        ) {
          Text(
            text = "PickTime",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
          )

          TimePicker(
            hours = mHours,
            setHours = setHours,
            minutes = mMinutes,
            setMinutes = setMinutes,
            onDone = {
              onGetResult(convertToMillis(mHours, mMinutes))
              onDismiss()
            }
          )

          Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 32.dp)
          ) {
            Button(
              onClick = onDismiss,
              colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black.copy(alpha = 0.07f),
                contentColor = Color.Black
              ),
              elevation = null,
              modifier = Modifier.width(90.dp)
            ) {
              Text(text = "Cancel")
            }

            Button(
              onClick = {
                onGetResult(convertToMillis(mHours, mMinutes))
                onDismiss()
              },
              modifier = Modifier.width(90.dp)
            ) {
              Text(text = "Save")
            }
          }
        }
      }
    }

  }

}

@Composable
fun TimePicker(
  hours: Int,
  setHours: (Int) -> Unit,
  minutes: Int,
  setMinutes: (Int) -> Unit,
  onDone: () -> Unit
) {
  val focusManager = LocalFocusManager.current

  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    TimePickerItem(
      value = hours,
      setValue = setHours,
      maxValue = 24,
      modifier = Modifier.align(Alignment.CenterVertically),
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
      ),
      keyboardActions = KeyboardActions(
        onNext = {
          focusManager.moveFocus(FocusDirection.Right)
        }
      )
    )
    Text(
      text = ":",
      style = MaterialTheme.typography.h3,
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .padding(horizontal = 4.dp),

      textAlign = TextAlign.Center
    )
    TimePickerItem(
      value = minutes,
      setValue = setMinutes,
      maxValue = 60,
      modifier = Modifier.align(Alignment.CenterVertically),
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number
      ),
      keyboardActions = KeyboardActions(
        onDone = {
          focusManager.clearFocus()
          onDone()
        }
      )
    )
  }
}

@Composable
private fun TimePickerItem(
  value: Int,
  setValue: (Int) -> Unit,
  maxValue: Int,
  modifier: Modifier = Modifier,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions
) {

  var textFieldValue by remember {
    mutableStateOf(TextFieldValue(formatValue(value)))
  }

  val interactionSource by remember {
    mutableStateOf(MutableInteractionSource())
  }

  val isFocused by interactionSource.collectIsFocusedAsState()

  LaunchedEffect(isFocused) {
    textFieldValue = textFieldValue.copy(
      selection = if (isFocused) {
        TextRange(start = 0, end = textFieldValue.text.length)
      } else {
        TextRange.Zero
      }
    )
  }

  TextField(
    value = textFieldValue,
    onValueChange = {
      var newText = it.text
      val typedCharacter = newText.lastOrNull() ?: return@TextField
      if (!typedCharacter.isDigit()) return@TextField

      val newValue = newText.toIntOrNull() ?: 0

      if (newValue < maxValue) {
        if (newText.length > 2)
          newText = newText.drop(1)

        textFieldValue = it.copy(text = newText, selection = TextRange(newText.length))
        setValue(newValue)
      }
      else {
        textFieldValue = it.copy(text = typedCharacter.toString(), selection = TextRange(newText.length))
        setValue(typedCharacter.digitToInt())
      }
    },
    interactionSource = interactionSource,
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = true,
    textStyle = MaterialTheme.typography.h3.copy(textAlign = TextAlign.Center),
    modifier = modifier
      .width(120.dp)
      .wrapContentHeight()
  )

}