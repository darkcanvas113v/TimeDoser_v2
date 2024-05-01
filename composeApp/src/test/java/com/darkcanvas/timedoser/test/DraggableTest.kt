package com.darkcanvas.timedoser.test

import org.darkcanvas.timedoser.features.main_screen.ui.calculateOffsets
import kotlin.test.Test

class DraggableTest {

  @Test
  fun calculateOffsetsTest() {
    val result1 = calculateOffsets(
      offset = -1,
      size = 4,
      pos = 1
    )
    assert(result1.isSameAs(listOf(1, -1, 0, 0)))

    val result2 = calculateOffsets(
      offset = 1,
      size = 4,
      pos = 1
    )
    assert(result2.isSameAs(listOf(0, 1, -1, 0)))

    val result3 = calculateOffsets(
      offset = 2,
      size = 4,
      pos = 1
    )
    assert(result3.isSameAs(listOf(0, 2, -1, -1)))

    val result4 = calculateOffsets(
      offset = 6,
      size = 4,
      pos = 1
    )
    assert(result4.isSameAs(listOf(0, 2, -1, -1)))

    val result5 = calculateOffsets(
      offset = -5,
      size = 4,
      pos = 1
    )
    assert(result5.isSameAs(listOf(1, -1, 0, 0)))
  }

  private fun<T> List<T>.isSameAs(list: List<T>): Boolean {
    if (size != list.size) return false

    forEachIndexed { index, t ->
      if (list[index] != t) return false
    }
    return true
  }

}