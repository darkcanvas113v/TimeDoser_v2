package org.darkcanvas.timedoser.features.task_editor.model

data class TaskEditorErrorState(
  val nameIsEmpty: Boolean = false,
  val durationIsZero: Boolean = false
)
