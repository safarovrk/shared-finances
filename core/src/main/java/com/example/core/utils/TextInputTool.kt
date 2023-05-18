package com.example.core.utils

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

class TextInputTool(
    initialText: String = "",
    val singleLine: Boolean = true,
    val maxLength: Int = Int.MAX_VALUE,
    val keyboardOptions: KeyboardOptions
) {
    fun onFocusChanged(hasFocus: Boolean) {
        this.hasFocus = hasFocus
    }

    /*fun onVisibilityChanged(isVisible: Boolean) {
        this.isVisible = isVisible
    }*/

    var text: String by mutableStateOf(initialText)
    //var isVisible: Boolean by mutableStateOf(true)
    var enabled: Boolean by mutableStateOf(true)
    var hasFocus: Boolean by mutableStateOf(false)
    var error: String? by mutableStateOf(null)
}