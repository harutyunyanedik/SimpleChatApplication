package com.example.chatapplication.presentation.theme.enums

enum class ScreenResolutionEnum(private val screenWidth: Int) {
    XHdpi(200),
    XXHdpi(300),
    XXXHdpi(400);

    companion object {
        private val map = entries.associateBy(ScreenResolutionEnum::screenWidth)
        fun from(value: Int) = map[value] ?: XXHdpi
    }
}