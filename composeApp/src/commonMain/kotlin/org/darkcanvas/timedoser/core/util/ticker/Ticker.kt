package org.darkcanvas.timedoser.core.util.ticker

import kotlinx.coroutines.flow.Flow

interface Ticker {
  fun observeTicks(): Flow<Long>
  fun start(interval: Long)
  fun stop()
}