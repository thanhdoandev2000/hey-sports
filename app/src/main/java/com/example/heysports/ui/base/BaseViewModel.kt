package com.example.heysports.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heysports.cores.events.AppEventBus
import com.example.heysports.cores.events.AppEvents
import com.example.heysports.cores.extensions.castTo
import com.example.heysports.cores.extensions.getValue
import com.example.heysports.data.models.response.AnyApiRequest
import com.example.heysports.data.models.response.ApiRequest
import com.example.heysports.data.models.response.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.cancellation.CancellationException

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

    protected fun callApis(
        requests: List<AnyApiRequest<*>>,
        isLoading: Boolean = true,
        isFinishLoading: Boolean = true,
        isThrowError: Boolean = true,
        onErrors: (List<Exception>) -> Unit = {},
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch {
            if (isLoading) loadingReducer?.let { updateState { it(true) } }
            val errorList = mutableListOf<Exception>()
            val listMutex = Mutex()

            val jobs = requests.map { req ->
                launch {
                    try {
                        when (val result = req.request()) {
                            is NetworkResult.Success -> {
                                @Suppress("UNCHECKED_CAST")
                                (req.onSuccess as (Any?) -> Unit)(result.value)
                            }

                            else -> {
                                result.castTo<NetworkResult.Error>()?.let { error ->
                                    listMutex.withLock { errorList.add(error.exception) }
                                    if (isThrowError) {
                                        sendEffectGlobal(AppEvents.ShowGlobalError(error.message))
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        if (e is CancellationException) throw e

                        listMutex.withLock { errorList.add(e) }
                        if (isThrowError) {
                            sendEffectGlobal(
                                AppEvents.ShowGlobalError(e.message ?: "Unknown Error")
                            )
                        }
                    }
                }
            }

            jobs.joinAll()
            if (errorList.isNotEmpty()) onErrors(errorList)
            onDone()

            if (isFinishLoading) loadingReducer?.let { updateState { it(false) } }
        }
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
                        onSuccess(result.value)
                        if (isFinishLoading) loadingReducer?.let { updateState { it(false) } }
                    }

                    else -> {
                        loadingReducer?.let { updateState { it(false) } }
                        handleError(isThrowError, result, onError)
                    }
                }
            } catch (e: Exception) {
                if (isThrowError) sendEffectGlobal(AppEvents.ShowGlobalError(e.message.getValue()))
                loadingReducer?.let { updateState { it(false) } }
            }
        }
    }

    private fun <T> handleError(
        isThrowError: Boolean = true,
        error: NetworkResult<T>,
        onError: (Exception) -> Unit
    ) {
        when (error) {
            is NetworkResult.Error -> {
                if (isThrowError) sendEffectGlobal(AppEvents.ShowGlobalError(error.message))
                onError(error.exception)
            }

            else -> {}
        }
    }
}