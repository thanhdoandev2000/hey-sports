package com.example.heysports.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.cores.events.AppEventBus
import com.example.heysports.cores.events.AppEvents
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.data.model.UiEffect
import com.example.heysports.data.model.UiState
import com.example.heysports.data.networks.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : UiState, E : UiEffect>(
    initialState: S,
    private val loadingReducer: (S.(Boolean) -> S)? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _effect by lazy { Channel<E>(Channel.BUFFERED) }
    val effect by lazy { _effect.receiveAsFlow() }

    protected fun updateState(reducer: S.() -> S) {
        _uiState.update { it.reducer() }
    }

    protected fun sendEffect(effect: E) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun sendEffectGlobal(effect: AppEvents) {
        viewModelScope.launch { AppEventBus.emit(effect) }
    }

    protected fun <T> callApis(
        requests: List<suspend () -> Unit>,
        isLoading: Boolean = true,
    ) {

    }

    protected fun <T> callApi(
        request: suspend () -> NetworkResult<T>,
        isLoading: Boolean = true,
        isFinishLoading: Boolean = true,
        isThrowError: Boolean = true,
        onSuccess: (T) -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        viewModelScope.launch {
            if (isLoading) loadingReducer?.let { updateState { it(true) } }
            try {
                when (val result = request()) {
                    is NetworkResult.Success -> {
                        onSuccess(result.data)
                        if (isFinishLoading) loadingReducer?.let { updateState { it(false) } }
                    }

                    else -> {
                        loadingReducer?.let { updateState { it(false) } }
                        handleError(result, onError)
                    }
                }
            } catch (e: Exception) {
                sendEffectGlobal(AppEvents.ShowGlobalError(e.message.getValue()))
                loadingReducer?.let { updateState { it(false) } }
            }
        }
    }

    private fun <T> handleError(error: NetworkResult<T>, onError: (Exception) -> Unit) {
        when (error) {
            is NetworkResult.Error -> {
                sendEffectGlobal(AppEvents.ShowGlobalError(error.message))
                viewModelScope.launch {
                    delay(500)
                    sendEffectGlobal(AppEvents.ShowGlobalError(error.message))
                }
                onError(error.exception)
            }

            else -> {}
        }
    }
}