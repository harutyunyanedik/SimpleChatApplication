package com.example.mangochatapplication.presentation.shared.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.interviewalphab.R
import com.example.mangochatapplication.common.utils.EMPTY_STRING
import com.example.mangochatapplication.presentation.shared.model.CountriesEnum

@Composable
fun PhoneNumber(
    hint: String = stringResource(id = R.string.global_phone_number),
    defaultCountry: CountriesEnum = CountriesEnum.RUSSIA,
    onPhoneNumberChanged: (String) -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    onCountryChanged: (CountriesEnum) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    var phoneNumber by remember { mutableStateOf(EMPTY_STRING) }
    var selectedCountry by remember { mutableStateOf(defaultCountry) }
    var errorText by remember { mutableStateOf<String?>(null) }
    var isFocused by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
    ) {
        Text(
            text = hint.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary),
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                onPhoneNumberChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (!isFocused) {
                        validatePhoneNumber(phoneNumber, selectedCountry) {
                            errorText = it
                        }
                    }
                    onFocusChanged(isFocused)
                },
            label = {
                Text(hint)
            },
            isError = errorText != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ),
            leadingIcon = {
                CountryCodeDropdown(
                    selectedCountry = selectedCountry,
                    onCountryCodeChanged = { newCountry ->
                        selectedCountry = newCountry
                        onCountryChanged(newCountry)
                    }
                )
            },
            trailingIcon = {
                if (isFocused) {
                    IconButton(onClick = { phoneNumber = EMPTY_STRING }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            singleLine = true,
        )
        if (!errorText.isNullOrEmpty()) {
            Text(
                text = errorText ?: EMPTY_STRING,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun CountryCodeDropdown(
    countriesEnum: List<CountriesEnum> = listOf(CountriesEnum.USA, CountriesEnum.RUSSIA, CountriesEnum.ARMENIA),
    selectedCountry: CountriesEnum,
    onCountryCodeChanged: (CountriesEnum) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { expanded = !expanded }
        ) {
            Image(
                painter = painterResource(id = selectedCountry.flag),
                contentDescription = stringResource(id = R.string.global_country_flag),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 36.dp, height = 24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = selectedCountry.code, style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = stringResource(id = R.string.global_dropdown_arrow),
                modifier = Modifier
                    .size(16.dp)
                    .padding(top = 4.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countriesEnum.forEach { country ->
                DropdownMenuItem(text = {
                    Row {
                        Image(
                            painter = painterResource(id = country.flag),
                            contentDescription = stringResource(id = R.string.global_country_flag),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(width = 36.dp, height = 24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(id = country.titleResId))
                    }
                }, onClick = {
                    onCountryCodeChanged(country)
                    expanded = false
                })
            }
        }
    }
}

fun validatePhoneNumber(phoneNumber: String, country: CountriesEnum, onError: (String?) -> Unit) {
    if (phoneNumber.isNotEmpty() && !country.regex.matches(phoneNumber)) {
        val pattern = Regex("\\[0-9]\\{(\\d+)\\}").find(country.regex.pattern)
        pattern?.groupValues?.get(1)?.let {
            onError("Phone number must contain only $it numbers")
        }
    } else {
        onError(null)
    }
}