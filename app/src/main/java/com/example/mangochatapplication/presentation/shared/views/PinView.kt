package com.example.mangochatapplication.presentation.shared.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PinView(
    modifier: Modifier = Modifier,
    length: Int = 6,
    color: Color = MaterialTheme.colorScheme.onBackground,
    borderColor: Color = Color.Transparent,
    boxWidth: Dp = 45.dp,
    boxHeight: Dp = 45.dp,
    radius: Dp = 8.dp,
    strokeSize: Dp = 1.dp,
    fontSize: TextUnit = 16.sp,
    fontFamily: FontFamily? = FontFamily.SansSerif,
    fontColor: Color = MaterialTheme.colorScheme.background,
    value: String = "",
    disableKeypad: Boolean = false,
    mask: String? = null,
    isCursorVisible: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    ),
    onValueChanged: (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    BasicTextField(
        modifier = modifier,
        readOnly = disableKeypad,
        value = value,
        keyboardOptions = keyboardOptions,
        onValueChange = {
            if (it.length <= length && it.all { c -> c in '0'..'9' }) {
                onValueChanged(it)
                if (it.length >= length) {
                    keyboard?.hide()
                }
            }
        }, decorationBox = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(length) { index ->
                    PinCell(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(boxWidth, boxHeight)
                            .border(
                                BorderStroke(strokeSize, borderColor),
                                shape = RoundedCornerShape(radius)
                            )
                            .background(color = color, shape = RoundedCornerShape(radius))
                            .padding(8.dp),
                        value = value.getOrNull(index),
                        obscureText = mask,
                        isCursorVisible = value.length == index && isCursorVisible,
                        fontSize = fontSize,
                        fontFamily = fontFamily,
                        fontColor = fontColor
                    )
                    if (index != length - 1)
                        Spacer(modifier = Modifier.size(8.dp))
                }
            }
        })
}

@Composable
private fun PinCell(
    modifier: Modifier,
    value: Char?,
    isCursorVisible: Boolean = false,
    obscureText: String?,
    fontSize: TextUnit = 16.sp,
    fontFamily: FontFamily? = FontFamily.SansSerif,
    fontColor: Color = MaterialTheme.colorScheme.background,
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        Text(
            text = if (isCursorVisible) cursorSymbol else if (!obscureText.isNullOrBlank() && !value?.toString().isNullOrBlank()) obscureText else value?.toString() ?: "",
            fontSize = fontSize,
            fontFamily = fontFamily,
            color = fontColor,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}