package com.example.chatapplication.presentation.navigation

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
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
import com.example.chatapplication.data.di.tokenDataStoreQualifierName
import com.example.chatapplication.data.storage.TokenDataStore
import com.example.chatapplication.presentation.feature.chat.screen.ChatScreen
import com.example.chatapplication.presentation.feature.editprofile.screen.EditProfileScreen
import com.example.chatapplication.presentation.feature.phonenumber.screen.PhoneNumberScreen
import com.example.chatapplication.presentation.feature.profile.screen.ProfileScreen
import com.example.chatapplication.presentation.feature.registation.screen.RegistrationScreen
import com.example.chatapplication.presentation.feature.smsverification.screen.SmsVerificationScreen
import com.example.chatapplication.presentation.navigation.routes.Screens
import com.example.chatapplication.presentation.navigation.utils.navigateAndClearBackStack
import com.example.chatapplication.presentation.shared.utils.activityViewModel
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileEffect
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileIntent
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named

@Composable
fun ChatNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Launch.route) {
        composable(route = Screens.Launch.route) {
            LaunchScreen(navController)
        }
        composable(route = Screens.PhoneNumberScreen.route) {
            PhoneNumberScreen(navController = navController)
        }
        composable(route = Screens.SmsVerificationScreen.withArgsPath("phone", "code"), arguments = listOf(navArgument("phone") { type = NavType.StringType })) {
            SmsVerificationScreen(phone = it.arguments?.getString("phone"), countryCode = it.arguments?.getString("code"), navController = navController)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screens.EditProfile.route) {
            EditProfileScreen()
        }
        composable(route = Screens.Chat.route) {
            ChatScreen(navController = navController)
        }
        composable(route = Screens.Registration.withArgsPath("phone"), arguments = listOf(navArgument("phone") { type = NavType.StringType })) {
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
                navController?.navigateAndClearBackStack(Screens.PhoneNumberScreen.route)
            }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            profileViewModel.profileEffects.collectLatest {
                when (it) {
                    is ProfileEffect.ProfileGot -> {
                        if (it.data != null && it.error == null) {
                            navController?.navigateAndClearBackStack(Screens.Chat.route)
                        }
                    }
                }
            }
        }
    }
}