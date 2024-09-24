package com.example.chatapplication.presentation.base.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

abstract class BaseSideEffectViewModel<Intent : BaseIntent, State : BaseState, Effect : BaseEffect> : BaseViewModel<Intent, State>() {

    protected abstract val effects: Channel<Effect>

    fun addSideEffect(effect: Effect) {
        viewModelScope.launch {
            effects.send(effect)
        }
    }
}