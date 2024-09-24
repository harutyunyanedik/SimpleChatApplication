package com.example.chatapplication.common.utils.net

import com.example.chatapplication.common.utils.net.model.ApiWrapper
import com.example.chatapplication.data.apiservice.RefreshTokenApiService
import com.example.chatapplication.data.model.refreshtoken.RefreshTokenRequest
import com.example.chatapplication.data.storage.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val tokenDataStore: TokenDataStore,
    private val refreshTokenApiService: RefreshTokenApiService
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            tokenDataStore.getAccessToken()
        }
        synchronized(this) {
            val updatedToken = runBlocking {
                tokenDataStore.getAccessToken()
            }
            val token = if (currentToken != updatedToken) updatedToken else {
                val newSessionResponse = runBlocking {
                    parseResponse {
                        refreshTokenApiService.refreshToken(RefreshTokenRequest(tokenDataStore.getRefreshToken()))
                    }
                }
                if (newSessionResponse is ApiWrapper.Success) {
                    newSessionResponse.data?.let { data ->
                        runBlocking {
                            data.accessToken?.let { tokenDataStore.saveAccessToken(it) }
                            data.refreshToken?.let { tokenDataStore.saveRefreshToken(it) }
                        }
                        data.accessToken
                    }
                } else null
            }
            return if (token != null) response.request.newBuilder()
                .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
                .build() else null
        }
    }
}