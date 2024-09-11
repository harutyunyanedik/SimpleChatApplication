package com.example.mangochatapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mangochatapplication.data.di.tokenDataStoreQualifierName
import com.example.mangochatapplication.data.tokenmanager.TokenDataStore
import com.example.mangochatapplication.presentation.feature.auth.editprofile.screen.EditProfileScreen
import com.example.mangochatapplication.presentation.feature.auth.phonenumber.screen.PhoneNumberScreen
import com.example.mangochatapplication.presentation.feature.auth.profile.screen.ProfileScreen
import com.example.mangochatapplication.presentation.feature.auth.registation.screen.RegistrationScreen
import com.example.mangochatapplication.presentation.feature.auth.smsverification.screen.SmsVerificationScreen
import com.example.mangochatapplication.presentation.feature.chat.screen.ChatScreen
import com.example.mangochatapplication.presentation.navigation.routes.Screens
import com.example.mangochatapplication.presentation.shared.utils.activityViewModel
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileEffect
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileIntent
import com.example.mangochatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun ChatNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Launch.route) {
        composable(route = Screens.Launch.route) {
            LaunchScreen(navController)
        }
        composable(route = Screens.PhoneNumberScreen.route) {
            PhoneNumberScreen(navController)
        }
        composable(route = "${Screens.SmsVerificationScreen.route}/{phone}/{code}", arguments = listOf(navArgument("phone") { type = NavType.StringType })) {
            SmsVerificationScreen(phone = it.arguments?.getString("phone"), countryCode = it.arguments?.getString("code"), navController = navController)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }
        composable(route = Screens.EditProfile.route) {
            EditProfileScreen()
        }
        composable(route = Screens.Chat.route) {
            ChatScreen()
        }
        composable(route = "${Screens.Registration.route}/{phone}", arguments = listOf(navArgument("phone") { type = NavType.StringType })) {
            RegistrationScreen(phone = it.arguments?.getString("phone"), navController = navController)
        }
    }
}

@TestOnly
@Composable
fun LaunchScreen(navController: NavHostController?, profileViewModel: ProfileViewModel = activityViewModel()) {

    val tokenStore = get<TokenDataStore>(qualifier = named(tokenDataStoreQualifierName))
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            if (tokenStore.getAccessToken() != null) {
                profileViewModel.addIntent(ProfileIntent.GetMe)
            } else {
                navController?.navigate(Screens.PhoneNumberScreen.route)
            }
        }
    }

    SideEffect {
        scope.launch {
            profileViewModel.profileEffects.collectLatest {
                when (it) {
                    is ProfileEffect.ProfileGot -> {
                        if (it.data != null && it.error == null) {
                            navController?.navigate(Screens.Chat.route)
                        }
                    }
                }
            }
        }
    }
}