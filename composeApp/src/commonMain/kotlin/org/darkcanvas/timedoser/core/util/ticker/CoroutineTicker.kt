package org.darkcanvas.timedoser.core.util.ticker

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CoroutineTicker: Ticker {

  private val ticks = MutableSharedFlow<Long>()

  private var counter: Job? = null

  private var lastUpdateTime = 0L

  override fun observeTicks(): Flow<Long> {
    return ticks
  }

  override fun start(interval: Long) {
    lastUpdateTime = System.currentTimeMillis()

    counter = CoroutineScope(Dispatchers.Default).launch {
      while (true) {
        ticks.emit(System.currentTimeMillis() - lastUpdateTime)
        lastUpdateTime = System.currentTimeMillis()
        delay(interval)
      }
    }
  }

  override fun stop() {
    counter?.cancel()
  }

}