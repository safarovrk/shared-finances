package com.example.features.sharedfinance.home.journal.presentation.create_dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFTextField
import com.example.features.R
import com.example.features.sharedfinance.home.journal.presentation.JournalEvent
import com.example.features.sharedfinance.home.journal.presentation.JournalViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun CreateNoteDialog(
    viewModel: JournalViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onSubmit: (String, String, String) -> Unit,
) {
    val state = viewModel.dialogCreationState.value
    val keyboardController = LocalSoftwareKeyboardController.current

    val categoriesState = viewModel.categoriesState.value

    var expanded by remember {
        mutableStateOf(false)
    }

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
                    text = viewModel.sumCreationTextState.value.text,
                    modifier = Modifier.wrapContentWidth().padding(8.dp),
                    onValueChange = {
                        viewModel.onEvent(JournalEvent.EnteredSum(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    label = { Text(stringResource(R.string.sum)) },
                    readOnly = false
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    SFTextField(
                        text = viewModel.categoryCreationTextState.value.text,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.category)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() }),
                        modifier = Modifier.wrapContentWidth().padding(8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categoriesState.categories.forEach { selectedOption ->
                            DropdownMenuItem(onClick = {
                                viewModel.onEvent(JournalEvent.EnteredCategory(selectedOption.name))
                                expanded = false
                            }) {
                                Text(text = selectedOption.name)
                            }
                        }
                    }
                }
                SFTextField(
                    text = viewModel.commentCreationTextState.value.text,
                    modifier = Modifier.wrapContentWidth().padding(8.dp),
                    onValueChange = {
                        viewModel.onEvent(JournalEvent.EnteredComment(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    label = { Text(stringResource(R.string.comment)) },
                    readOnly = false
                )
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
                        .padding(16.dp)
                        .animateContentSize(),
                    onClick = {
                        onSubmit(
                            viewModel.sumCreationTextState.value.text,
                            viewModel.categoryCreationTextState.value.text,
                            viewModel.commentCreationTextState.value.text,
                        )
                    },

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
