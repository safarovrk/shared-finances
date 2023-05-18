/*
package com.example.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.theme.CustomTheme
import com.example.core.utils.TextInputTool

@Composable
fun TextViewRoot(
    textInputTool: TextInputTool,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    */
/*unpressedLeadingIcon: @Composable (() -> Unit)? = null,
    pressedLeadingIcon: @Composable (() -> Unit)? = null,
    unpressedTrailingIcon: @Composable (() -> Unit)? = null,
    pressedTrailingIcon: @Composable (() -> Unit)? = null,*//*

    textFieldHeight: Dp = Dp.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    keyboardActions: KeyboardActions? = null
) {

    Column(
        modifier = modifier
    ) {
        val focusRequester = remember { FocusRequester() }

        SideEffect {
            if (textInputTool.hasFocus) {
                focusRequester.requestFocus()
            } else {
                focusRequester.freeFocus()
            }
        }

        OutlinedTextField(

            value = textInputTool.text,


            //visualTransformation = visualTransformation,

            //leadingIcon = if (textInputTool.hasFocus) pressedLeadingIcon else unpressedLeadingIcon,
            //trailingIcon = if (textInputTool.hasFocus) pressedTrailingIcon else unpressedTrailingIcon,


        )

        OutlinedTextField(
            value = textInputTool.text,
            modifier = Modifier
                .fillMaxWidth()
                .height(textFieldHeight)
                .focusRequester(focusRequester)
                .onFocusChanged { textInputTool.onFocusChanged(it.isFocused) },
            onValueChange = textInputTool::onTextChanged,
            isError = textInputTool.error != null,
            placeholder = {
                Text(
                    placeholder,
                    style = CustomTheme.typography.text.mediumNormal
                )
            },
            //leadingIcon = if (textInputTool.hasFocus) pressedLeadingIcon else unpressedLeadingIcon,
            //trailingIcon = if (textInputTool.hasFocus) pressedTrailingIcon else unpressedTrailingIcon,
            textStyle = CustomTheme.typography.text.mediumNormal,
            colors = textFieldColors(
                enabled = textInputTool.enabled,
                pressed = textInputTool.hasFocus,
                isError = textInputTool.error != null
            ),
            singleLine = textInputTool.singleLine,
            maxLines = maxLines,
            keyboardOptions = textInputTool.keyboardOptions,
            keyboardActions = keyboardActions ?: KeyboardActions(),
            enabled = textInputTool.enabled
        )
        ErrorText(textInputTool.error?.resolve(LocalContext.current)?.toString())
    }
}

@Composable
fun textFieldColors(
    enabled: Boolean,
    pressed: Boolean,
    isError: Boolean,
): TextFieldColors {
    val elementsColor = when {
        !enabled -> CustomTheme.colors.text.disabled
        pressed || isError -> CustomTheme.colors.text.button
        else -> CustomTheme.colors.text.primary
    }
    val backgroundColor = when {
        !enabled -> CustomTheme.colors.background.disabled
        pressed -> CustomTheme.colors.background.invert
        isError -> CustomTheme.colors.background.formError
        else -> CustomTheme.colors.background.secondary
    }
    return TextFieldDefaults.outlinedTextFieldColors(
        textColor = elementsColor,
        backgroundColor = backgroundColor,
        leadingIconColor = elementsColor,
        trailingIconColor = elementsColor,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        cursorColor = elementsColor,
        placeholderColor = elementsColor
    )
}

*/
/*@Composable
fun ErrorText(
    errorText: String?,
    modifier: Modifier = Modifier
) {
    errorText?.let {
        Text(
            modifier = modifier.padding(top = 8.dp),
            text = it,
            style = CustomTheme.typography.caption.regularSmall,
            color = CustomTheme.colors.text.alert
        )
    }
}

@Composable
fun textFieldColors(
    enabled: Boolean,
    pressed: Boolean,
    isError: Boolean,
): TextFieldColors {
    val elementsColor = when {
        !enabled -> CustomTheme.colors.text.disabled
        pressed || isError -> CustomTheme.colors.text.button
        else -> CustomTheme.colors.text.primary
    }
    val backgroundColor = when {
        !enabled -> CustomTheme.colors.background.disabled
        pressed -> CustomTheme.colors.background.invert
        isError -> CustomTheme.colors.background.formError
        else -> CustomTheme.colors.background.secondary
    }
    return TextFieldDefaults.outlinedTextFieldColors(
        textColor = elementsColor,
        backgroundColor = backgroundColor,
        leadingIconColor = elementsColor,
        trailingIconColor = elementsColor,
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        cursorColor = elementsColor,
        placeholderColor = elementsColor
    )
}

@Preview(showBackground = true)
@Composable
fun OrdinaryTextFieldPreview() {
    val inputControl = InputControl(
        initialText = "initialText",
        singleLine = false,
        maxLength = 16,
        keyboardOptions = KeyboardOptions.Default,
    )
    AppTheme {
        OrdinaryTextField(
            modifier = Modifier
                .fillMaxWidth(),
            inputControl = inputControl,
            placeholder = "Placeholder"
        )
    }
}*/

