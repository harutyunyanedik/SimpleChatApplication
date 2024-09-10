package com.example.mangochatapplication.presentation.feature.auth.smsverification

import androidx.lifecycle.viewModelScope
import com.example.mangochatapplication.common.utils.Recourse
import com.example.mangochatapplication.common.utils.net.utils.parseErrorBody
import com.example.mangochatapplication.common.utils.safeLet
import com.example.mangochatapplication.data.model.checkcode.CheckCodeErrorDto
import com.example.mangochatapplication.data.tokenmanager.TokenDataStore
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.presentation.base.viewmodel.BaseSideEffectViewModel
import com.example.mangochatapplication.presentation.feature.auth.smsverification.screen.SmsVerificationScreenEffect
import com.example.mangochatapplication.presentation.feature.auth.smsverification.screen.SmsVerificationScreenIntent
import com.example.mangochatapplication.presentation.feature.auth.smsverification.screen.SmsVerificationScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch


class SmsVerificationViewModel(
    private val repository: MangoChatRepository,
    private val dataStore: TokenDataStore
) : BaseSideEffectViewModel<SmsVerificationScreenIntent, SmsVerificationScreenState, SmsVerificationScreenEffect>() {

    override val states: MutableStateFlow<SmsVerificationScreenState> = MutableStateFlow(SmsVerificationScreenState())
    val smsVerificationState: StateFlow<SmsVerificationScreenState>
        get() = states

    override val intents: MutableSharedFlow<SmsVerificationScreenIntent> = MutableSharedFlow()
    override val effects: Channel<SmsVerificationScreenEffect> = Channel()
    val smsVerificationEffects: Flow<SmsVerificationScreenEffect>
        get() = effects.consumeAsFlow()

    override fun handleIntent(intent: SmsVerificationScreenIntent) {
        when (intent) {
            is SmsVerificationScreenIntent.Initial -> updateState(states.value.copy(phone = intent.phone))

            is SmsVerificationScreenIntent.UpdateResendTimer -> updateState(states.value.copy(resendTime = intent.tick))

            is SmsVerificationScreenIntent.SendCode -> viewModelScope.launch {
                repository.sendAuthCode(intent.phone).collectLatest {
                    when (it) {
                        is Recourse.Loading -> updateState(states.value.copy(isLoading = true))
                        is Recourse.Error -> {
                            updateState(states.value.copy(isLoading = false, error = it.error))
                            addSideEffect(SmsVerificationScreenEffect.CodeSent())
                        }

                        is Recourse.Success -> {
                            updateState(states.value.copy(isLoading = false, error = null))
                            addSideEffect(SmsVerificationScreenEffect.CodeSent(it.data == true))
                        }
                    }
                }
            }

            is SmsVerificationScreenIntent.Verify -> {
                viewModelScope.launch {
                    repository.checkAuthCode(phone = intent.phone, code = intent.code).collectLatest {

                        when (it) {
                            is Recourse.Loading -> updateState(states.value.copy(isLoading = true))
                            is Recourse.Error -> {
                                val error = parseErrorBody(it.error ?: "", CheckCodeErrorDto::class)
                                updateState(states.value.copy(isLoading = false, error = error?.detail?.message))
                                addSideEffect(SmsVerificationScreenEffect.Verified(error = error?.detail?.message))
                            }

                            is Recourse.Success -> {
                                updateState(states.value.copy(isLoading = false, error = null))
                                addSideEffect(SmsVerificationScreenEffect.Verified(it.data))
                            }
                        }
                    }
                }
            }

            is SmsVerificationScreenIntent.UpdatePinValue -> {
                updateState(states.value.copy(pinValue = intent.value))
                if (intent.value.length == 6) {
                    states.value.phone?.let { phone ->
                        addIntent(SmsVerificationScreenIntent.Verify(phone, intent.value))
                    }
                }
            }

            is SmsVerificationScreenIntent.SaveToken -> {
                safeLet(intent.accessToken, intent.refreshToken) { accessToken, refreshToken ->
                    viewModelScope.launch {
                        dataStore.saveAccessToken(accessToken)
                        dataStore.saveRefreshToken(refreshToken)
                        addSideEffect(SmsVerificationScreenEffect.TokensSaved)
                    }
                }
            }
        }
    }
}