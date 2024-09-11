package com.example.mangochatapplication.presentation.shared.model

import com.example.interviewalphab.R

enum class CountriesEnum(val code: String, val titleResId: Int, val flag: Int, val regex: Regex) {
    ARMENIA("+374", R.string.global_armenia, R.drawable.flag_armenia, Regex("[0-9]{8}\$")),
    RUSSIA("+7", R.string.global_russia, R.drawable.flag_russia, Regex("[0-9]{10}\$")),
    USA("+1", R.string.global_usa, R.drawable.flag_united_states, Regex("[0-9]{10}\$"));

    companion object {
        private val map = entries.associateBy(CountriesEnum::code)
        fun from(code: String) = map[code] ?: RUSSIA
    }
}