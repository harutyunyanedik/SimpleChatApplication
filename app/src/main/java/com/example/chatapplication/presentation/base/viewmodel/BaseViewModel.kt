package com.example.chatapplication.presentation.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Intent : BaseIntent, State : BaseState> : ViewModel() {
    protected abstract val states: MutableStateFlow<State>
    abstract fun handleIntent(intent: Intent)

    private val intents: MutableSharedFlow<Intent> = MutableSharedFlow()

    init {
        viewModelScope.launch {
            intents.collect { intent ->
                handleIntent(intent)
            }
        }
    }

    fun addIntent(intent: Intent) {
        Log.d("EH_TAG", "BaseViewModel intent: $intent")
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    protected fun updateState(state: State) {
        states.update {
            state
        }
    }
}