package com.example.chatapplication.common.utils.extensions

import android.util.Patterns

fun String.isValidUrl(): Boolean = this.startsWith("http://")
        || this.startsWith("https://")
        && Patterns.WEB_URL.matcher(this).matches()

/**
 * Replaces numeric placeholders in the format `{n}` with
 * corresponding values from the provided keys.
 *
 * - Only numeric placeholders (e.g., `{0}`, `{1}`) are replaced.
 * - Non-numeric or out-of-bound placeholders remain unchanged.
 *
 * ### Example:
 * ```
 * "Question {0} of {1}".formatMessage("2", "6") // Output: Question 2 of 6
 * "Value: {a}".formatMessage("Test") // Output: Value: {a}
 * ```
 */
fun String.formatMessage(vararg keys: String): String {
    if (isEmpty() || keys.isEmpty()) return this

    return buildString {
        var i = 0

        while (i < this@formatMessage.length) {
            if (this@formatMessage[i] == '{') {
                val end = this@formatMessage.indexOf('}', i + 1)
                if (end > i) {
                    val placeholder = this@formatMessage.substring(i + 1, end)
                    val keyIndex = placeholder.toIntOrNull()

                    if (keyIndex != null && keyIndex in keys.indices) {
                        append(keys[keyIndex])
                        i = end + 1
                        continue
                    }
                }
            }
            // Append current character as is
            append(this@formatMessage[i])
            i++
        }
    }
}

