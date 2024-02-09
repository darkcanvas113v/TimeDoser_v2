package org.darkcanvas.timedoser.core.util

import org.darkcanvas.timedoser.data_domain.day_component.domain.model.Task

fun<T> List<T>.modifyAt(
  pos: Int,
  value: T
): List<T> = toMutableList().apply { this[pos] = value }

fun<T> List<T>.modifyFromTo(
  from: Int,
  to: Int = size,
  mutator: (T) -> T
): List<T> {
  val result = toMutableList()
  for (i in from until to) {
    result[i] = mutator(result[i])
  }
  return result
}

fun<T> List<T>.addItem(item: T): List<T> = toMutableList().apply {
  add(item)
}

fun<T> List<T>.removeItemAt(pos: Int) = toMutableList().apply {
  removeAt(pos)
}