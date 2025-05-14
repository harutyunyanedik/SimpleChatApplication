package com.example.chatapplication.presentation.feature.registation.screen

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.interviewalphab.R
import com.example.chatapplication.common.utils.EMPTY_STRING
import com.example.chatapplication.common.utils.extensions.safeLet
import com.example.chatapplication.presentation.feature.registation.RegistrationViewModel
import com.example.chatapplication.presentation.navigation.routes.Screens
import com.example.chatapplication.presentation.navigation.utils.navigateAndClearBackStack
import com.example.chatapplication.presentation.shared.utils.activityViewModel
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileEffect
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileIntent
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import com.example.chatapplication.presentation.shared.views.CenterTitledToolbar
import com.example.chatapplication.presentation.shared.views.LoadingButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = activityViewModel(),
    navController: NavHostController?,
    phone: String?
) {
    val focusRequester = remember { FocusRequester() }
    val state = viewModel.registrationState.collectAsStateWithLifecycle().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.addIntent(RegistrationIntent.Initial(phone))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f, fill = false)
        ) {
            CenterTitledToolbar(stringResource(id = R.string.global_registration), containerColor = MaterialTheme.colorScheme.background) {
                navController?.navigateUp()
            }
            Spacer(modifier = Modifier.height(24.dp))
            RegistrationTextField(
                state.phone ?: EMPTY_STRING, stringResource(id = R.string.global_phone), onValueChanged = {
                    viewModel.addIntent(RegistrationIntent.UserNameValueChanged(it))
                },
                focusRequester = focusRequester,
                enabled = false
            )
            Spacer(modifier = Modifier.height(8.dp))
            RegistrationTextField(
                state.nameValue ?: EMPTY_STRING, stringResource(id = R.string.global_name), state.nameErrorText, onValueChanged = {
                    viewModel.addIntent(RegistrationIntent.NameValueChanged(it))
                },
                focusRequester = focusRequester
            )
            Spacer(modifier = Modifier.height(8.dp))
            RegistrationTextField(
                state.usernameValue ?: EMPTY_STRING, stringResource(id = R.string.global_username), state.usernameErrorText, onValueChanged = {
                    viewModel.addIntent(RegistrationIntent.UserNameValueChanged(it))
                },
                focusRequester = focusRequester
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
        LoadingButton(
            isLoading = state.isLoading == true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            onClick = {
                keyboardController?.hide()
                viewModel.addIntent(RegistrationIntent.Validate)
            }) {
            Text(text = stringResource(id = R.string.global_register))
        }
    }

    SideEffect {
        scope.launch {
            viewModel.registrationEffects.collectLatest {
                when (it) {
                    is RegistrationEffect.Registered -> {
                        when {
                            it.data != null && it.error == null -> {
                                viewModel.addIntent(RegistrationIntent.SaveToken(accessToken = it.data.accessToken, refreshToken = it.data.refreshToken))
                            }

                            it.error != null -> {
                                Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    is RegistrationEffect.Validated -> {
                        if (it.isValid) {
                            val registrationState = viewModel.registrationState.value
                            safeLet(registrationState.nameValue, registrationState.usernameValue, registrationState.phone) { name, username, phone ->
                                viewModel.addIntent(RegistrationIntent.Register(name, phone, username))
                            }
                        }
                    }

                    RegistrationEffect.TokensSaved -> profileViewModel.addIntent(ProfileIntent.GetMe)
                }
            }
        }
        scope.launch {
            profileViewModel.profileEffects.collectLatest {
                when (it) {
                    is ProfileEffect.ProfileGot -> {
                        when {
                            it.data != null && it.error == null -> navController?.navigateAndClearBackStack(Screens.Chat.route)

                            it.error != null && it.data == null -> Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationTextField(
    value: String,
    hint: String,
    errorText: String? = null,
    onFocusChanged: (Boolean) -> Unit = {},
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager = LocalFocusManager.current,
    onValueChanged: (String) -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    enabled: Boolean = true
) {
    var isFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
    ) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    onFocusChanged(isFocused)
                },
            label = {
                Text(hint)
            },
            isError = errorText != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
        )
        if (!errorText.isNullOrEmpty()) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(phone = "", navController = null)
}