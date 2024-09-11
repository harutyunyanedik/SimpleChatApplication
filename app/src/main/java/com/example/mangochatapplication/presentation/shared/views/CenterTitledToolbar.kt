package com.example.mangochatapplication.presentation.shared.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.interviewalphab.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterTitledToolbar(
    title: String,
    containerColor: Color = Color.Blue,
    scrolledContainerColor: Color = Color.White,
    navigationIconContentColor: Color = Color.White,
    titleContentColor: Color = Color.White,
    actionIconContentColor: Color = Color.White,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(id = R.string.global_back)
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor,
            navigationIconContentColor = navigationIconContentColor,
            titleContentColor = titleContentColor,
            actionIconContentColor = actionIconContentColor,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}