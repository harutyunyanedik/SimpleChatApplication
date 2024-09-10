package com.example.mangochatapplication.presentation.shared.model

import com.example.interviewalphab.R

enum class CountriesEnum(val code: String, val title: String, val flag: Int, val regex: Regex) {
    ARMENIA("+374", "Armenia", R.drawable.flag_armenia, Regex("[0-9]{8}\$")),
    RUSSIA("+7", "Russia", R.drawable.flag_russia, Regex("[0-9]{10}\$")),
    USA("+1", "United States", R.drawable.flag_united_states, Regex("[0-9]{10}\$"));

    companion object {
        private val map = entries.associateBy(CountriesEnum::code)
        fun from(code: String) = map[code] ?: RUSSIA
    }
}