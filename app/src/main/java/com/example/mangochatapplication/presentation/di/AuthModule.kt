package com.example.mangochatapplication.presentation.di

import com.example.mangochatapplication.data.MangoChatNetworkAdapter
import com.example.mangochatapplication.data.MangoChatNetworkPort
import com.example.mangochatapplication.data.MangoChatRepositoryImpl
import com.example.mangochatapplication.domain.MangoChatRepository
import com.example.mangochatapplication.presentation.feature.auth.registation.RegistrationViewModel
import com.example.mangochatapplication.presentation.feature.auth.smsverification.SmsVerificationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<MangoChatNetworkPort> {
        MangoChatNetworkAdapter(get())
    }

    single<MangoChatRepository> {
        MangoChatRepositoryImpl(get())
    }

    viewModel<SmsVerificationViewModel> {
        SmsVerificationViewModel(get())
    }

    viewModel<RegistrationViewModel> {
        RegistrationViewModel(get())
    }
}