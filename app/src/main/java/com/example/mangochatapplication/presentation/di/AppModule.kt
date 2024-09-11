package com.example.mangochatapplication.presentation.di

import com.example.mangochatapplication.data.MangoChatNetworkAdapter
import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.MangoChatRepositoryImpl
import com.example.mangochatapplication.data.di.tokenDataStoreQualifierName
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.presentation.feature.auth.phonenumber.PhoneNumberScreenViewModel
import com.example.mangochatapplication.presentation.feature.auth.registation.RegistrationViewModel
import com.example.mangochatapplication.presentation.feature.auth.smsverification.SmsVerificationViewModel
import com.example.mangochatapplication.presentation.feature.chat.ChatViewModel
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<MangoChatNetworkPort> {
        MangoChatNetworkAdapter(get(), get())
    }

    single<MangoChatRepository> {
        MangoChatRepositoryImpl(get())
    }

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