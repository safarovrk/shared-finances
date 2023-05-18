package com.example.features.sharedfinance.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.SaveLoginUseCase
import com.example.core.utils.Resource
import com.example.core.utils.TextFieldState
import com.example.features.R
import com.example.features.sharedfinance.login.domain.LoginRequestBody
import com.example.features.sharedfinance.login.domain.LoginUseCase
import com.example.core.shared_preferences.domain.SaveTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveLoginUseCase: SaveLoginUseCase
) : ViewModel() {

    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
        object LoginSuccess : UiEvent()
    }

    companion object {
        const val INCORRECT_DATA_CODE = 401
        const val LOGIN_SUCCESS_CODE = 200
    }

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _loginFieldState = mutableStateOf(TextFieldState())
    val loginFieldState: State<TextFieldState> = _loginFieldState

    private val _passwordFieldState = mutableStateOf(TextFieldState())
    val passwordFieldState: State<TextFieldState> = _passwordFieldState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredLogin -> {
                _loginFieldState.value = loginFieldState.value.copy(
                    text = event.value
                )
            }

            is LoginEvent.EnteredPassword -> {
                _passwordFieldState.value = passwordFieldState.value.copy(
                    text = event.value
                )
            }

            is LoginEvent.LogIn -> {

                viewModelScope.launch {

                    if (!isFieldsValid()) return@launch

                    val potentialLogin = loginFieldState.value.text
                    loginUseCase(
                        LoginRequestBody(
                            login = potentialLogin,
                            password = /*HashGenerator.hashPass(*/passwordFieldState.value.text
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == LOGIN_SUCCESS_CODE) {
                                    try {
                                        result.data!!.body()?.let { token ->
                                            saveTokenUseCase(token.jwtToken).onEach { isTokenSaved ->
                                                when (isTokenSaved) {
                                                    true -> {
                                                        saveLoginUseCase(potentialLogin).onEach { isLoginSaved ->
                                                            when(isLoginSaved) {
                                                                true -> _eventFlow.emit(UiEvent.LoginSuccess)
                                                                false -> UiEvent.ShowSnackBar(R.string.cant_save_token)
                                                            }
                                                        }.launchIn(viewModelScope)
                                                    }
                                                    false -> _eventFlow.emit(
                                                        UiEvent.ShowSnackBar(R.string.cant_save_token)
                                                    )
                                                }
                                            }.launchIn(viewModelScope)
                                        }
                                    } catch (e: NullPointerException) {
                                        _eventFlow.emit(
                                            UiEvent.ShowSnackBar(R.string.unexpected_error)
                                        )
                                    }
                                } else if (result.data?.code() == INCORRECT_DATA_CODE) {
                                    _eventFlow.emit(
                                        UiEvent.ShowSnackBar(
                                            messageId = R.string.incorrect_login_or_password
                                        )
                                    )
                                } else {
                                    UiEvent.ShowSnackBar(
                                        messageId = R.string.unexpected_error
                                    )
                                }
                                _state.value = LoginState(isLoading = false)
                            }

                            is Resource.Error -> {
                                _eventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        messageId = result.messageId ?: R.string.unexpected_error
                                    )
                                )
                                _state.value = LoginState(isLoading = false)
                            }

                            is Resource.Loading -> {
                                _state.value = LoginState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private suspend fun isFieldsValid(): Boolean {

        val login = _loginFieldState.value.text
        val password = _passwordFieldState.value.text

        if (login == "" || password == "") {
            _eventFlow.emit(UiEvent.ShowSnackBar(R.string.enter_full_data))
            return false
        }

        return true
    }
}