package com.example.chatapplication.presentation.feature.chat.chatinput

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.chatapplication.common.utils.EMPTY_STRING

@SuppressLint("UnrememberedMutableState")
@Composable
internal fun ChatInput(
    modifier: Modifier = Modifier,
    onMessageChange: (String) -> Unit,
) {

    val context = LocalContext.current

    var input by remember { mutableStateOf(TextFieldValue(EMPTY_STRING)) }
    val textEmpty: Boolean by derivedStateOf { input.text.isEmpty() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.Bottom
    ) {

        TextField(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .weight(1f)
                .focusable(true),
            value = input,
            onValueChange = { input = it },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Message")
            },
            leadingIcon = {
                IconButton(onClick = {
                    Toast.makeText(
                        context,
                        "Emoji Clicked.\n(Not Available)",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                    Icon(imageVector = Icons.Filled.Mood, contentDescription = "Mood")
                }
            },
            trailingIcon = {
                Row {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Attach File Clicked.\n(Not Available)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.AttachFile, contentDescription = "File")
                    }
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Camera Clicked.\n(Not Available)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(imageVector = Icons.Filled.CameraAlt, contentDescription = "Camera")
                    }
                }

            }

        )
        Spacer(modifier = Modifier.padding(2.dp))
        FloatingActionButton(
            shape = CircleShape,
            onClick = {
                if (!textEmpty) {
                    onMessageChange(input.text)
                    input = TextFieldValue("")
                } else {
                    Toast.makeText(
                        context,
                        "Sound Recorder Clicked.\n(Not Available)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        ) {
            Icon(
                imageVector = if (textEmpty) Icons.Filled.Mic else Icons.AutoMirrored.Filled.Send,
                contentDescription = null
            )
        }
    }
}