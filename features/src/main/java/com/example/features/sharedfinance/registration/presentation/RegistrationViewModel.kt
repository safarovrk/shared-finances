package com.example.features.sharedfinance.registration.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.utils.Resource
import com.example.core.utils.TextFieldState
import com.example.core.utils.HashGenerator
import com.example.features.R
import com.example.features.sharedfinance.registration.domain.RegisterRequest
import com.example.features.sharedfinance.registration.domain.RegistrationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
        object Register : UiEvent()
    }

    companion object {
        const val CREATED_RESPONSE_CODE = 201
        const val EXIST_RESPONSE_CODE = 500
    }

    private val _state = mutableStateOf(RegistrationState())
    val state: State<RegistrationState> = _state

    private val _signupLoginState = mutableStateOf(TextFieldState())
    val signupLoginState: State<TextFieldState> = _signupLoginState

    private val _signupPasswordState = mutableStateOf(TextFieldState())
    val signupPasswordState: State<TextFieldState> = _signupPasswordState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.EnteredLogin -> {
                _signupLoginState.value = signupLoginState.value.copy(
                    text = event.value
                )
            }

            is RegistrationEvent.EnteredPassword -> {
                _signupPasswordState.value = signupPasswordState.value.copy(
                    text = event.value
                )
            }


            is RegistrationEvent.Register -> {


                viewModelScope.launch {

                    if (!isFieldsValid()) return@launch

                    registrationUseCase(
                        RegisterRequest(
                            login = signupLoginState.value.text,
                            password = /*HashGenerator.hashPass(*/signupPasswordState.value.text
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == CREATED_RESPONSE_CODE) {
                                    _eventFlow.emit(UiEvent.Register)
                                } else if (result.data?.code() == EXIST_RESPONSE_CODE) {
                                    _eventFlow.emit(
                                        UiEvent.ShowSnackBar(
                                            messageId = R.string.registration_failed_already_exist
                                        )
                                    )
                                } else {
                                    UiEvent.ShowSnackBar(
                                        messageId = result.messageId ?: R.string.unexpected_error
                                    )
                                }
                                _state.value = RegistrationState(isLoading = false)
                            }

                            is Resource.Error -> {
                                _state.value = RegistrationState(
                                    errorMessageId = result.messageId ?: R.string.unexpected_error
                                )
                                _eventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        messageId = result.messageId ?: R.string.unexpected_error
                                    )
                                )
                                _state.value = RegistrationState(isLoading = false)
                            }

                            is Resource.Loading -> {
                                _state.value = RegistrationState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    private suspend fun isFieldsValid(): Boolean {

        val login = _signupLoginState.value.text
        val password = _signupPasswordState.value.text

        if (login == "" || password == "") {
            _eventFlow.emit(UiEvent.ShowSnackBar(R.string.enter_full_data))
            return false
        }
        if (login.contains(" ") || password.contains(" ")) {

            _eventFlow.emit(UiEvent.ShowSnackBar(R.string.login_password_have_spaces))
            return false
        }
        if (password.length !in 6..20) {
            _eventFlow.emit(UiEvent.ShowSnackBar(R.string.password_length_7_20))
            return false
        }
        if (login.length > 24) {
            _eventFlow.emit(UiEvent.ShowSnackBar(R.string.login_length_less_24))
            return false
        }
        return true
    }
}