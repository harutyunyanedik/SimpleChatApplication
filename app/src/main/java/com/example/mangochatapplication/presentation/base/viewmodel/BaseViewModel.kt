package com.example.mangochatapplication.presentation.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<Intent : BaseIntent, State : BaseState> : ViewModel() {
    protected abstract val states: MutableStateFlow<State>
    protected abstract val intents: MutableSharedFlow<Intent>
    abstract fun handleIntent(intent: Intent)

    fun addIntent(intent: Intent) {
        Log.d("EH_TAG", "intent: $intent")
        handleIntent(intent)
    }

    protected fun updateState(state: State) {
        states.update {
            state
        }
    }
}