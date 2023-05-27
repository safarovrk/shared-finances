package com.example.features.sharedfinance.home.settings.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetJournalUseCase
import com.example.core.shared_preferences.domain.GetLoginUseCase
import com.example.core.shared_preferences.domain.GetRoleUseCase
import com.example.core.shared_preferences.domain.LogOutUseCase
import com.example.core.utils.Constants
import com.example.core.utils.Resource
import com.example.core.utils.TextFieldState
import com.example.core.utils.UserRoleState
import com.example.features.R
import com.example.features.sharedfinance.home.settings.domain.ExitJournalRequest
import com.example.features.sharedfinance.home.settings.domain.ExitJournalUseCase
import com.example.features.sharedfinance.home.settings.domain.InviteRequest
import com.example.features.sharedfinance.home.settings.domain.InviteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val inviteUserUseCase: InviteUserUseCase,
    private val getLoginUseCase: GetLoginUseCase,
    private val getJournalUseCase: GetJournalUseCase,
    private val exitJournalUseCase: ExitJournalUseCase
) : ViewModel() {

    sealed class UiEvent {
        object LogOut : UiEvent()
        object RouteToChangeJournal : UiEvent()
        object RouteToInvitations : UiEvent()
        data class ShowSnackBar(val messageId: Int) : UiEvent()
    }

    companion object {
        const val USER_NOT_FOUND_CODE = 404
        const val ALREADY_USING_CODE = 409
        const val INVITATION_SENT_CODE = 201
        const val ALREADY_SENT_CODE = 303
        const val SUCCESS_CODE = 200
        const val INTERNAL_ERROR_CODE = 500

    }

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    private val _dialogState = mutableStateOf(InviteUserDialogState())
    val dialogState: State<InviteUserDialogState> = _dialogState

    private val _loginTextState = mutableStateOf(TextFieldState())
    val loginTextState: State<TextFieldState> = _loginTextState

    private val _userRoleState = mutableStateOf(UserRoleState())
    val userRoleState: State<UserRoleState> = _userRoleState

    private val _currentInfoState = mutableStateOf(CurrentInfoState())
    val currentInfoState: State<CurrentInfoState> = _currentInfoState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getRole()
        loadInfo()
    }


    fun onEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {

                is SettingsEvent.OnInvitationSubmit -> {
                    inviteUser(event.userName, event.role)
                }

                is SettingsEvent.OnInvitationDismiss -> {
                    _state.value = SettingsState(
                        isDialogShown = false
                    )
                    _dialogState.value = InviteUserDialogState()
                    _loginTextState.value = TextFieldState()
                }

                is SettingsEvent.ChangeJournalClicked -> {
                    _eventFlow.emit(UiEvent.RouteToChangeJournal)
                }

                is SettingsEvent.InvitationsClicked -> {
                    _eventFlow.emit(UiEvent.RouteToInvitations)
                }

                is SettingsEvent.InviteUserClicked -> {
                    _state.value = SettingsState(
                        isDialogShown = true
                    )
                }

                is SettingsEvent.LogOutClicked -> {
                    logOutUseCase.invoke()
                    _eventFlow.emit(UiEvent.LogOut)
                }

                is SettingsEvent.EnteredUserName -> {
                    _loginTextState.value = loginTextState.value.copy(
                        text = event.value
                    )
                }

                is SettingsEvent.ExitJournalClicked -> {
                    exitJournal()
                }
            }
        }
    }

    private fun getRole() {
        viewModelScope.launch {
            _userRoleState.value = UserRoleState(getRoleUseCase.invoke())
        }
    }

    private fun loadInfo() {
        viewModelScope.launch {
            val roleString: String = when(getRoleUseCase.invoke()) {
                Constants.ADMIN_ROLE_ID -> Constants.ADMIN_ROLE
                Constants.ADULT_ROLE_ID -> Constants.ADULT_ROLE
                Constants.KID_ROLE_ID -> Constants.KID_ROLE
                else -> {""}
            }
            _currentInfoState.value = CurrentInfoState(
                login = getLoginUseCase.invoke(),
                journalName = getJournalUseCase.invoke(),
                role = roleString
            )
        }
    }

    private fun exitJournal() {
        viewModelScope.launch {
            exitJournalUseCase(
                ExitJournalRequest(
                    login = getLoginUseCase.invoke(),
                    journalName = getJournalUseCase.invoke()
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        when(result.data?.code()) {
                            SUCCESS_CODE -> {
                                _eventFlow.emit(UiEvent.RouteToChangeJournal)
                            }
                            INTERNAL_ERROR_CODE -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                            }
                        }
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.ShowSnackBar(result.messageId ?: R.string.unexpected_error))
                    }
                    is Resource.Loading -> { }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun inviteUser(userName: String, role: String) {
        viewModelScope.launch {

            if(!isFieldValid()) return@launch

            inviteUserUseCase(
                InviteRequest(
                    login = userName,
                    journalName = getJournalUseCase.invoke(),
                    role = role,
                    admin = getLoginUseCase.invoke()
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        when(result.data?.code()) {
                            INVITATION_SENT_CODE -> {
                                onEvent(SettingsEvent.OnInvitationDismiss)
                                _dialogState.value = InviteUserDialogState(isLoading = false)
                            }
                            USER_NOT_FOUND_CODE -> {
                                _dialogState.value = InviteUserDialogState(
                                    errorMessageId = R.string.user_not_found
                                )
                            }
                            ALREADY_USING_CODE -> {
                                _dialogState.value = InviteUserDialogState(
                                    errorMessageId = R.string.user_already_using
                                )
                            }
                            ALREADY_SENT_CODE -> {
                                _dialogState.value = InviteUserDialogState(
                                    errorMessageId = R.string.invitation_already_sent
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        _dialogState.value = InviteUserDialogState(
                            errorMessageId = result.messageId ?: R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        _dialogState.value = InviteUserDialogState(
                            isLoading = true,
                            errorMessageId = _dialogState.value.errorMessageId
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun isFieldValid(): Boolean {

        val login = _loginTextState.value.text

        if (login == "") {
            _dialogState.value = InviteUserDialogState(errorMessageId = R.string.enter_full_data)
            return false
        }
        return true
    }
}