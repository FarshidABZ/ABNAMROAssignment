package com.abnamro.base.mvibase

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private interface BaseViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> {
    val viewState: StateFlow<S>
    val singleEvent: Flow<E>

    fun processIntent(intent: I)
}

abstract class BaseMviViewModel<I : MviIntent, S : MviViewState, E : MviSingleEvent> :
    BaseViewModel<I, S, E>, ViewModel() {

    private var _singleEvent = Channel<E>()
    override val singleEvent = _singleEvent.receiveAsFlow()

    protected fun sendSingleEvent(event: E) {
        viewModelScope.launch {
            _singleEvent.send(event)
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        _singleEvent.close()
    }
}