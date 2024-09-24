package com.example.chatapplication.presentation.navigation.routes

sealed class Screens(val route: String) {

    data object Launch : Screens("Launch")
    data object PhoneNumberScreen : Screens("PhoneNumberScreen")
    data object SmsVerificationScreen : Screens("SmsVerificationScreen")
    data object Profile : Screens("Profile")
    data object EditProfile : Screens("EditProfile")
    data object Registration : Screens("Registration")
    data object Chat : Screens("Chat")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }

    fun withArgsPath(vararg args: String?): String {
        return buildString {
            append("{$route}")
            args.forEach { args ->
                append("/{$args}")
            }
        }
    }
}