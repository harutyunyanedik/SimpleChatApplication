package com.example.mangochatapplication.data.di


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.mangochatapplication.common.utils.net.utils.AccessTokenInterceptor
import com.example.mangochatapplication.common.utils.net.utils.AuthAuthenticator
import com.example.mangochatapplication.data.apiservice.AuthenticatedMangoWebService
import com.example.mangochatapplication.data.apiservice.RefreshTokenService
import com.example.mangochatapplication.data.apiservice.UnauthenticatedMangoChatWebService
import com.example.mangochatapplication.data.tokenmanager.TokenDataStore
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.factory
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
    single<OkHttpClient.Builder>(qualifier = named(okHttpClientBuilderQualifierName)) {
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
            .build()
    }

    single<OkHttpClient>(qualifier = named(publicClientQualifierName)) {
        get<OkHttpClient.Builder>(qualifier = named(okHttpClientBuilderQualifierName))
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

    single<AuthenticatedMangoWebService> {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>(qualifier = named(authenticatedClientQualifierName))).build()
            .create(AuthenticatedMangoWebService::class.java)
    }

    single<RefreshTokenService>(qualifier = named(refreshTokenServiceQualifierName)) {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>(qualifier = named(tokenRefreshClientQualifierName))).build()
            .create(RefreshTokenService::class.java)
    }
    single<UnauthenticatedMangoChatWebService> {
        get<Retrofit.Builder>()
            .client(get<OkHttpClient>(qualifier = named(publicClientQualifierName))).build()
            .create(UnauthenticatedMangoChatWebService::class.java)
    }
}

internal const val authenticatedClientQualifierName = "AuthenticatedClient"
internal const val refreshTokenServiceQualifierName = "RefreshTokenService"
internal const val tokenDataStoreQualifierName = "TokenDataStore"
internal const val okHttpClientBuilderQualifierName = "OkHttpClientBuilder"
internal const val publicClientQualifierName = "PublicClient"
internal const val tokenRefreshClientQualifierName = "TokenRefreshClient"
internal const val authAuthenticatorQualifierName = "AuthAuthenticator"
internal const val accessTokenInterceptorQualifierName = "AccessTokenInterceptor"
internal const val dataStoreFileName = "app.preferences_pb"
internal const val BASE_URL = "https://plannerok.ru/"

val Context.dataStore by preferencesDataStore(name = dataStoreFileName)
