package com.example.chatapplication.presentation.di

import com.example.chatapplication.data.ChatNetworkAdapter
import com.example.chatapplication.data.ChatNetworkPort
import com.example.chatapplication.data.ChatRepositoryImpl
import com.example.chatapplication.data.di.tokenDataStoreQualifierName
import com.example.chatapplication.domain.ChatRepository
import com.example.chatapplication.presentation.feature.auth.phonenumber.PhoneNumberScreenViewModel
import com.example.chatapplication.presentation.feature.auth.registation.RegistrationViewModel
import com.example.chatapplication.presentation.feature.auth.smsverification.SmsVerificationViewModel
import com.example.chatapplication.presentation.feature.chat.ChatViewModel
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    viewModel<SmsVerificationViewModel> {
        SmsVerificationViewModel(get(), get(qualifier = named(tokenDataStoreQualifierName)))
    }

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(get(), get(qualifier = named(tokenDataStoreQualifierName)))
    }

    viewModel<ProfileViewModel> {
        ProfileViewModel(get())
    }

    viewModel<ChatViewModel> {
        ChatViewModel()
    }

    viewModel<PhoneNumberScreenViewModel> {
        PhoneNumberScreenViewModel()
    }
}