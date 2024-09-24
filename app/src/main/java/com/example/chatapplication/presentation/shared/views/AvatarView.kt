package com.example.chatapplication.presentation.shared.views

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.interviewalphab.R

@Composable
fun AvatarView(
    modifier: Modifier,
    size: Dp = 142.dp,
    isAvatarEditMode: Boolean = false,
    avatar: String? = null,
    onAvatarChanged: (String?) -> Unit = {},
    defaultAvatar: @Composable (() -> Unit)? = null,
    nestedContainerPadding: PaddingValues = PaddingValues(1.dp)
) {
    var pickedAvatar by remember { mutableStateOf(avatar) }
    var cachedImage by remember { mutableStateOf(pickedAvatar?.let { decodeBase64Image(it) }) }

    LaunchedEffect(pickedAvatar, avatar) {
        if (pickedAvatar != avatar && pickedAvatar != null) {
            cachedImage = decodeBase64Image(pickedAvatar!!)
        } else if (avatar != null) {
            cachedImage = decodeBase64Image(avatar)
        }
    }

    Box(
        modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(3.dp, MaterialTheme.colorScheme.secondary, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (cachedImage != null) {
            Image(
                bitmap = cachedImage!!.asImageBitmap(),
                contentDescription = "Avatar",
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(nestedContainerPadding),
                contentAlignment = Alignment.Center
            ) {
                defaultAvatar?.invoke() ?: DefaultAvatarIcon(size)
            }
        }

        if (isAvatarEditMode) {
            EditAvatarButton(onAvatarPicked = { newAvatar ->
                pickedAvatar = newAvatar
                onAvatarChanged(newAvatar)
            })
        }
    }
}

@Composable
fun DefaultAvatarIcon(size: Dp) {
    Icon(
        painter = painterResource(id = R.drawable.ic_person),
        contentDescription = "Default Avatar",
        modifier = Modifier.size(size).padding(16.dp)
    )
}

@Composable
fun EditAvatarButton(onAvatarPicked: (String?) -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission = isGranted
        }
    )

    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = {
        }) {
            Icon(Icons.Default.AccountCircle, contentDescription = "Pick Image", tint = Color.White)
        }
    }
}

fun decodeBase64Image(base64String: String): Bitmap? {
    return try {
        val bytes = Base64.decode(base64String, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    } catch (e: Exception) {
        null
    }
}