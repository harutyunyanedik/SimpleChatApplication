package com.example.mangochatapplication.common.utils.net.utils

import com.example.mangochatapplication.data.apiservice.RefreshTokenService
import com.example.mangochatapplication.data.model.refreshtoken.RefreshTokenRequest
import com.example.mangochatapplication.data.tokenmanager.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AuthAuthenticator(
    private val tokenDataStore: TokenDataStore,
    private val refreshTokenService: RefreshTokenService
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
                        refreshTokenService.refreshToken(RefreshTokenRequest(tokenDataStore.getRefreshToken()))
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