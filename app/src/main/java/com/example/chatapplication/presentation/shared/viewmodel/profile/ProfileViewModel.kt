package com.example.chatapplication.presentation.shared.viewmodel.profile

import androidx.lifecycle.viewModelScope
import com.example.chatapplication.common.utils.net.model.Recourse
import com.example.chatapplication.domain.ChatRepository
import com.example.chatapplication.presentation.base.viewmodel.BaseSideEffectViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ChatRepository) : BaseSideEffectViewModel<ProfileIntent, ProfileState, ProfileEffect>() {

    override val effects: Channel<ProfileEffect> = Channel()
    val profileEffects: Flow<ProfileEffect>
        get() = effects.receiveAsFlow()

    override val states: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState>
        get() = states

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