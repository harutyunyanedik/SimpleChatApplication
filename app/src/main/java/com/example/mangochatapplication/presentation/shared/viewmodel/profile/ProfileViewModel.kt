package com.example.mangochatapplication.presentation.shared.viewmodel.profile

import androidx.lifecycle.viewModelScope
import com.example.mangochatapplication.common.utils.Recourse
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.presentation.base.viewmodel.BaseSideEffectViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: MangoChatRepository) : BaseSideEffectViewModel<ProfileIntent, ProfileState, ProfileEffect>() {

    override val effects: Channel<ProfileEffect> = Channel()
    val profileEffects: Flow<ProfileEffect>
        get() = effects.consumeAsFlow()

    override val states: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState>
        get() = states

    override val intents: MutableSharedFlow<ProfileIntent> = MutableSharedFlow()

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.GetMe -> {
                viewModelScope.launch {
                    repository.me().collectLatest {
                        when (it) {
                            is Recourse.Loading -> updateState(states.value.copy(isLoading = true))
                            is Recourse.Error -> {
                                updateState(states.value.copy(isLoading = false, error = it.error))
                                addSideEffect(ProfileEffect.ProfileGot(error = it.error))
                            }

                            is Recourse.Success -> {
                                updateState(states.value.copy(isLoading = false, data = it.data))
                                addSideEffect(ProfileEffect.ProfileGot(data = it.data))
                            }
                        }
                    }
                }
            }
        }
    }
}