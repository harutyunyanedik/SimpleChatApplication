package com.example.chatapplication.data.di


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.chatapplication.common.utils.net.AccessTokenInterceptor
import com.example.chatapplication.common.utils.net.AuthAuthenticator
import com.example.chatapplication.data.ChatNetworkAdapter
import com.example.chatapplication.data.ChatNetworkPort
import com.example.chatapplication.data.ChatRepositoryImpl
import com.example.chatapplication.data.apiservice.ChatApiService
import com.example.chatapplication.data.apiservice.RefreshTokenApiService
import com.example.chatapplication.data.storage.TokenDataStore
import com.example.chatapplication.domain.ChatRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
val dataModule = module {
    single<TokenDataStore>(qualifier = named(tokenDataStoreQualifierName)) {
        TokenDataStore(get<Context>().dataStore)
    }
    single<AccessTokenInterceptor>(qualifier = named(accessTokenInterceptorQualifierName)) {
        AccessTokenInterceptor(get(qualifier = named(tokenDataStoreQualifierName)))
    }
    single<Authenticator>(qualifier = named(authAuthenticatorQualifierName)) {
        AuthAuthenticator(get(qualifier = named(tokenDataStoreQualifierName)), get(qualifier = named(refreshTokenServiceQualifierName)))
    }
    factory<OkHttpClient.Builder>(qualifier = named(okHttpClientBuilderQualifierName)) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    single<OkHttpClient>(qualifier = named(authenticatedClientQualifierName)) {
        get<OkHttpClient.Builder>(qualifier = named(okHttpClientBuilderQualifierName))
            .authenticator(get<Authenticator>(qualifier = named(authAuthenticatorQualifierName)))
            .addInterceptor(get<AccessTokenInterceptor>(qualifier = named(accessTokenInterceptorQualifierName)))
            .build()
    }

    single<OkHttpClient>(qualifier = named(tokenRefreshClientQualifierName)) {
        get<OkHttpClient.Builder>(qualifier = named(okHttpClientBuilderQualifierName))
            .addInterceptor(get<AccessTokenInterceptor>(qualifier = named(accessTokenInterceptorQualifierName)))
            .build()
    }

    single<Retrofit.Builder> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
        }
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    }

    single<ChatApiService> {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>(qualifier = named(authenticatedClientQualifierName))).build()
            .create(ChatApiService::class.java)
    }

    single<RefreshTokenApiService>(qualifier = named(refreshTokenServiceQualifierName)) {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>(qualifier = named(tokenRefreshClientQualifierName))).build()
            .create(RefreshTokenApiService::class.java)
    }

    single<ChatNetworkPort> {
        ChatNetworkAdapter(get())
    }

    single<ChatRepository> {
        ChatRepositoryImpl(get())
    }
}

internal const val authenticatedClientQualifierName = "AuthenticatedClient"
internal const val refreshTokenServiceQualifierName = "RefreshTokenService"
internal const val tokenDataStoreQualifierName = "TokenDataStore"
internal const val okHttpClientBuilderQualifierName = "OkHttpClientBuilder"
internal const val tokenRefreshClientQualifierName = "TokenRefreshClient"
internal const val authAuthenticatorQualifierName = "AuthAuthenticator"
internal const val accessTokenInterceptorQualifierName = "AccessTokenInterceptor"
internal const val dataStoreFileName = "app.preferences_pb"
internal const val BASE_URL = "https://plannerok.ru/"

val Context.dataStore by preferencesDataStore(name = dataStoreFileName)
