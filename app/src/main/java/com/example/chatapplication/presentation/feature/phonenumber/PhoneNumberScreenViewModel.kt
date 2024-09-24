package com.example.chatapplication.presentation.feature.phonenumber

import com.example.chatapplication.presentation.base.viewmodel.BaseSideEffectViewModel
import com.example.chatapplication.presentation.feature.phonenumber.screen.PhoneNumberScreenEffect
import com.example.chatapplication.presentation.feature.phonenumber.screen.PhoneNumberScreenIntent
import com.example.chatapplication.presentation.feature.phonenumber.screen.PhoneNumberScreenState
import com.example.chatapplication.presentation.shared.model.CountriesEnum
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class PhoneNumberScreenViewModel : BaseSideEffectViewModel<PhoneNumberScreenIntent, PhoneNumberScreenState, PhoneNumberScreenEffect>() {
    override val states: MutableStateFlow<PhoneNumberScreenState> = MutableStateFlow(PhoneNumberScreenState())
    val phoneNumberScreenState: StateFlow<PhoneNumberScreenState>
        get() = states

    override val effects: Channel<PhoneNumberScreenEffect> = Channel()
    val phoneNumberScreenEffect: Flow<PhoneNumberScreenEffect>
        get() = effects.receiveAsFlow()

    override fun handleIntent(intent: PhoneNumberScreenIntent) {
        when (intent) {
            is PhoneNumberScreenIntent.CountryChanged -> updateState(states.value.copy(selectedCountry = intent.code))
            is PhoneNumberScreenIntent.PhoneNumberChanged -> updateState(states.value.copy(phoneNumber = intent.phoneNumber))
            PhoneNumberScreenIntent.Validate -> {
                validatePhoneNumber(states.value.phoneNumber, states.value.selectedCountry) {
                    updateState(states.value.copy(error = it))
                    addSideEffect(PhoneNumberScreenEffect.Validated(error = it))
                }
            }
        }
    }

    private fun validatePhoneNumber(phoneNumber: String?, country: CountriesEnum, onError: (String?) -> Unit) {
        if (phoneNumber.isNullOrEmpty()) {
            onError("Phone Number is Required")
            return
        }
        if (phoneNumber.isNotEmpty() && !country.regex.matches(phoneNumber)) {
            val pattern = Regex("\\[0-9]\\{(\\d+)\\}").find(country.regex.pattern)
            pattern?.groupValues?.get(1)?.let {
                onError("Phone number must contain only $it numbers")
            }
        } else {
            onError(null)
        }
    }
}