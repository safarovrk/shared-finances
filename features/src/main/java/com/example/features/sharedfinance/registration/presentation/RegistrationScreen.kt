package com.example.features.sharedfinance.registration.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core.theme.CustomTheme
import com.example.core.ui.SFTextField
import com.example.features.R
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is RegistrationViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = context.resources.getString(event.messageId)
                    )
                }

                is RegistrationViewModel.UiEvent.Register -> {
                    navController.navigateUp()
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp)
            ) {
                Text(
                    text = stringResource(R.string.registration),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 32.sp
                )
                SFTextField(
                    text = viewModel.signupLoginState.value.text,
                    modifier = Modifier.padding(8.dp),
                    onValueChange = {
                        viewModel.onEvent(RegistrationEvent.EnteredLogin(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    label = {
                        Text(text = stringResource(R.string.login))
                    },
                    readOnly = false
                )
                SFTextField(
                    text = viewModel.signupPasswordState.value.text,
                    modifier = Modifier.padding(8.dp),
                    onValueChange = {
                        viewModel.onEvent(RegistrationEvent.EnteredPassword(it))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            if (passwordVisibility) {
                                Icon(
                                    imageVector = Icons.Filled.Visibility,
                                    contentDescription = Icons.Filled.Visibility.name
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.VisibilityOff,
                                    contentDescription = Icons.Filled.VisibilityOff.name
                                )
                            }

                        }
                    },
                    label = {
                        Text(text = stringResource(R.string.password))
                    },
                    readOnly = false
                )
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.onEvent(RegistrationEvent.Register)
                    },
                    modifier = Modifier.padding(8.dp).animateContentSize(),
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.CenterVertically).size(12.dp),
                                    color = CustomTheme.colors.text.button
                                )
                            }
                            Text(
                                text = stringResource(R.string.sign_up),
                                modifier = Modifier.padding(start = if (state.isLoading) 8.dp else 0.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}