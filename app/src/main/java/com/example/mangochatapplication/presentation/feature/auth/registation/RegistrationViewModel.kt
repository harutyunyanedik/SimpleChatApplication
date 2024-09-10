package com.example.mangochatapplication.presentation.feature.auth.registation

import androidx.lifecycle.viewModelScope
import com.example.mangochatapplication.common.utils.Recourse
import com.example.mangochatapplication.common.utils.net.utils.parseErrorBody
import com.example.mangochatapplication.common.utils.safeLet
import com.example.mangochatapplication.data.model.register.RegistrationErrorDto
import com.example.mangochatapplication.data.tokenmanager.TokenDataStore
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.presentation.base.viewmodel.BaseSideEffectViewModel
import com.example.mangochatapplication.presentation.feature.auth.registation.screen.RegistrationEffect
import com.example.mangochatapplication.presentation.feature.auth.registation.screen.RegistrationIntent
import com.example.mangochatapplication.presentation.feature.auth.registation.screen.RegistrationState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val repository: MangoChatRepository,
    private val dataStore: TokenDataStore
) : BaseSideEffectViewModel<RegistrationIntent, RegistrationState, RegistrationEffect>() {

    override val effects: Channel<RegistrationEffect> = Channel()
    val registrationEffects: Flow<RegistrationEffect>
        get() = effects.consumeAsFlow()

    override val states: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState())
    val registrationState: StateFlow<RegistrationState>
        get() = states

    override val intents: MutableSharedFlow<RegistrationIntent> = MutableSharedFlow()

    override fun handleIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.Initial -> updateState(states.value.copy(phone = intent.phone))
            is RegistrationIntent.Register -> {
                viewModelScope.launch {
                    repository.register(name = intent.name, phone = intent.phone, username = intent.username).collectLatest {
                        when (it) {
                            is Recourse.Error -> {
                                val error = parseErrorBody(it.error ?: "", RegistrationErrorDto::class)
                                updateState(states.value.copy(isLoading = false, error = error?.detail?.message))
                                addSideEffect(RegistrationEffect.Registered(error = error?.detail?.message))
                            }

                            is Recourse.Loading -> updateState(states.value.copy(isLoading = true, error = null))
                            is Recourse.Success -> {
                                updateState(states.value.copy(isLoading = false))
                                addSideEffect(RegistrationEffect.Registered(data = it.data))
                            }
                        }
                    }
                }
            }

            is RegistrationIntent.NameValueChanged -> updateState(states.value.copy(nameValue = intent.value))
            is RegistrationIntent.UserNameValueChanged -> updateState(states.value.copy(usernameValue = intent.value))
            is RegistrationIntent.Validate -> {
                val name = states.value.nameValue
                val username = states.value.usernameValue
                if (name.isNullOrEmpty()) {
                    updateState(states.value.copy(nameErrorText = "the field is required"))
                    return
                } else {
                    updateState(states.value.copy(nameErrorText = null))
                }
                if (username.isNullOrEmpty()) {
                    updateState(states.value.copy(usernameErrorText = "the field must contain minimum 5 character"))
                    return
                }
                if (username.length < 5) {
                    updateState(states.value.copy(usernameErrorText = "the field must contain minimum 5 character"))
                    return
                } else {
                    updateState(states.value.copy(usernameErrorText = null))
                }
                addSideEffect(RegistrationEffect.Validated(true))
            }

            is RegistrationIntent.SaveToken -> {
                safeLet(intent.accessToken, intent.refreshToken) { accessToken, refreshToken ->
                    viewModelScope.launch {
                        dataStore.saveAccessToken(accessToken)
                        dataStore.saveRefreshToken(refreshToken)
                        addSideEffect(RegistrationEffect.TokensSaved)
                    }
                }
            }
        }
    }
}