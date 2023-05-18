package com.example.features.sharedfinance.home.categories.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFTextField
import com.example.features.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CategoryCreationDialog(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    val state = viewModel.dialogState.value
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .wrapContentWidth()
                .animateContentSize()
        ) {
            Column(
                modifier = Modifier
                    .background(CustomTheme.colors.background.primary)
                    .padding(16.dp)
            ) {

                SFTextField(
                    text = viewModel.categoryTextState.value.text,
                    modifier = Modifier.wrapContentWidth(),
                    onValueChange = {
                        viewModel.onEvent(CategoriesEvent.EnteredCategoryName(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    label = { Text(stringResource(R.string.category_name)) },
                    readOnly = false
                )
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "${viewModel.categoryTextState.value.text.length}/30",
                        color = CustomTheme.colors.text.primary
                    )
                }
                if (state.errorMessageId != 0) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = stringResource(state.errorMessageId),
                            color = CustomTheme.colors.text.alert
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        onSubmit(viewModel.categoryTextState.value.text)
                    }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterVertically).size(12.dp),
                                color = CustomTheme.colors.text.button
                            )
                        }
                        Text(
                            text = stringResource(R.string.create),
                            modifier = Modifier.padding(start = if (state.isLoading) 8.dp else 0.dp)
                        )
                    }
                }
            }
        }

    }
}
