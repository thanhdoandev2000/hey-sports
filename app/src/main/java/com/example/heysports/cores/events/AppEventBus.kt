package com.example.heysports.cores.events

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AppEventBus {
    private val _globalEffect = MutableSharedFlow<AppEvents>(extraBufferCapacity = 1)
    val globalEffect = _globalEffect.asSharedFlow()

    fun emit(event: AppEvents) = _globalEffect.tryEmit(event)
}