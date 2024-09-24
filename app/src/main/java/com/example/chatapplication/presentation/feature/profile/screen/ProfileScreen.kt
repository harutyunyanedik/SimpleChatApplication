package com.example.chatapplication.presentation.feature.profile.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.interviewalphab.R
import com.example.chatapplication.common.utils.EMPTY_STRING
import com.example.chatapplication.presentation.shared.utils.activityViewModel
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileState
import com.example.chatapplication.presentation.shared.viewmodel.profile.ProfileViewModel
import com.example.chatapplication.presentation.shared.views.AvatarView

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = activityViewModel(), navController: NavHostController?) {
    val state = viewModel.profileState.collectAsStateWithLifecycle().value
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileToolbar(navController, state)
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(24.dp))
        AvatarView(modifier = Modifier, avatar = state.data?.avatar)
        Spacer(modifier = Modifier.height(16.dp))
        NameText(state = state)
        Spacer(modifier = Modifier.height(8.dp))
        PhoneNumberText(state = state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileToolbar(navController: NavHostController?, state: ProfileState) {
    TopAppBar(
        title = {
            NameRow(state = state)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController?.navigateUp()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.global_back)
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NameRow(state: ProfileState) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = stringResource(id = R.string.global_me), style = MaterialTheme.typography.titleLarge)
            Text(text = "Hello ${state.data?.name}", style = MaterialTheme.typography.titleSmall)
        }
        Text(
            text = stringResource(id = R.string.global_edit), modifier = Modifier
                .background(Color.Transparent, shape = RoundedCornerShape(12.dp))
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable {
                    Toast
                        .makeText(context, "Edit profile in dev", Toast.LENGTH_SHORT)
                        .show()
                },
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun NameText(state: ProfileState) {
    Text(
        text = state.data?.name ?: EMPTY_STRING,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(700), fontSize = 32.sp)
    )
}

@Composable
fun PhoneNumberText(state: ProfileState) {
    Text(
        text = "+${state.data?.phone}",
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyMedium
    )
}