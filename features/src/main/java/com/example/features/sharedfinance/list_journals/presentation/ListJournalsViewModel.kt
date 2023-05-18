package com.example.features.sharedfinance.list_journals.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetLoginUseCase
import com.example.core.shared_preferences.domain.LogOutUseCase
import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.list_journals.domain.GetJournalsUseCase
import com.example.core.shared_preferences.domain.SaveJournalUseCase
import com.example.core.shared_preferences.domain.SaveRoleUseCase
import com.example.core.utils.TextFieldState
import com.example.features.sharedfinance.home.journal.presentation.create_dialog.CreateNoteDialogState
import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import com.example.features.sharedfinance.list_journals.domain.CreateJournalUseCase
import com.example.features.sharedfinance.list_journals.domain.GetRoleNetworkUseCase
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import com.example.features.sharedfinance.list_journals.domain.RoleRequest
import com.example.features.sharedfinance.login.presentation.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListJournalsViewModel @Inject constructor(
    private val getJournalsUseCase: GetJournalsUseCase,
    private val saveJournalUseCase: SaveJournalUseCase,
    private val getLoginUseCase: GetLoginUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val createJournalUseCase: CreateJournalUseCase,
    private val saveRoleUseCase: SaveRoleUseCase,
    private val getRoleNetworkUseCase: GetRoleNetworkUseCase,
) : ViewModel() {

    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
        object JournalSelected : UiEvent()
        object TokenExpired : UiEvent()
        object InvitationButtonClicked : UiEvent()
        object LogoutButtonClicked : UiEvent()
    }

    companion object {
        const val LOADED_SUCCESS_CODE = 200
        const val CREATED_SUCCESS_CODE = 201
        const val TOKEN_EXPIRED_CODE = 403
        const val NOT_FOUND_CODE = 404
        const val JOURNAL_EXIST_CODE = 409
        const val MAX_LETTER_COUNT = 50
    }

    init {

        getJournals()
    }

    private val _state = mutableStateOf(ListJournalsState())
    val state: State<ListJournalsState> = _state

    private val _dialogState = mutableStateOf(JournalCreationDialogState())
    val dialogState: State<JournalCreationDialogState> = _dialogState

    private val _journalTextState = mutableStateOf(TextFieldState())
    val journalTextState: State<TextFieldState> = _journalTextState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: ListJournalsEvent) {
        when (event) {
            is ListJournalsEvent.JournalChosen -> {
                viewModelScope.launch {
                    saveJournalUseCase(event.journalName).onEach { isSaved ->
                        when (isSaved) {
                            true -> {
                                getRoleNetworkUseCase(
                                    RoleRequest(
                                        login = getLoginUseCase.invoke(),
                                        journalName = event.journalName
                                    )
                                ).onEach { roleId ->
                                    when (roleId) {
                                        is Resource.Success -> {
                                            saveRoleUseCase(
                                                roleId.data.toString()
                                            ).onEach {
                                                when (it) {
                                                    true -> {
                                                        _eventFlow.emit(UiEvent.JournalSelected)
                                                    }

                                                    false -> {
                                                        _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                                                    }
                                                }
                                            }.launchIn(viewModelScope)
                                        }

                                        is Resource.Error -> { }

                                        is Resource.Loading -> { }
                                    }
                                }.launchIn(viewModelScope)
                            }
                            false -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }

            is ListJournalsEvent.OnLogoutClicked -> {
                viewModelScope.launch {
                    logOutUseCase.invoke()
                    _eventFlow.emit(UiEvent.LogoutButtonClicked)
                }
            }

            is ListJournalsEvent.CreationClick -> {
                _state.value = ListJournalsState(
                    journals = _state.value.journals,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isDialogShown = true
                )
            }

            is ListJournalsEvent.OnCreationDismiss -> {
                _state.value = ListJournalsState(
                    journals = _state.value.journals,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isDialogShown = false
                )
                _dialogState.value = JournalCreationDialogState()
                _journalTextState.value = TextFieldState()
            }

            is ListJournalsEvent.OnCreationSubmit -> {
                createJournal(event.journalName)
            }

            is ListJournalsEvent.EnteredJournalName -> {
                if (event.value.length <= MAX_LETTER_COUNT) {
                    _journalTextState.value = journalTextState.value.copy(
                        text = event.value
                    )
                }
            }

            is ListJournalsEvent.OnInvitationsClicked -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.InvitationButtonClicked)
                }
            }
        }
    }

    private fun createJournal(journalName: String) {
        viewModelScope.launch {

            if (!isFieldsValid()) return@launch

            createJournalUseCase(
                CreateJournalRequest(
                    login = getLoginUseCase.invoke(),
                    journalName = journalName
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == CREATED_SUCCESS_CODE) {
                            onEvent(ListJournalsEvent.OnCreationDismiss)
                            //_dialogState.value = JournalCreationDialogState(isLoading = false)
                            getJournals()

                        } else if (result.data?.code() == JOURNAL_EXIST_CODE) {
                            _dialogState.value = JournalCreationDialogState(
                                errorMessageId = R.string.journal_already_exist
                            )
                        }
                    }

                    is Resource.Error -> {
                        _dialogState.value = JournalCreationDialogState(
                            errorMessageId = R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        _dialogState.value = JournalCreationDialogState(
                            isLoading = true,
                            errorMessageId = _dialogState.value.errorMessageId
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    fun getJournals() {
        viewModelScope.launch {
            getJournalsUseCase(
                JournalsRequest(getLoginUseCase.invoke())
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == LOADED_SUCCESS_CODE) {
                            try {
                                if (result.data!!.body()?.isEmpty() == true) {
                                    _state.value = ListJournalsState(
                                        errorMessageId = R.string.no_journals_found
                                    )
                                } else {
                                    _state.value = ListJournalsState(
                                        journals = result.data!!.body() ?: emptyList()
                                    )
                                }

                            } catch (e: NullPointerException) {
                                if (_state.value.journals.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                                else _state.value = ListJournalsState(errorMessageId = R.string.unexpected_error)
                            }
                        } else {
                            if (_state.value.journals.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                            else _state.value = ListJournalsState(errorMessageId = R.string.unexpected_error)
                        }
                    }

                    is Resource.Error -> {
                        if (_state.value.journals.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                        else _state.value = ListJournalsState(errorMessageId = R.string.unexpected_error)
                    }

                    is Resource.Loading -> {
                        if (_state.value.journals.isNotEmpty()) _state.value = ListJournalsState(isRefreshing = true)
                        else _state.value = ListJournalsState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun isFieldsValid(): Boolean {

        val journalName = _journalTextState.value.text

        if (journalName == "") {
            _dialogState.value =
                JournalCreationDialogState(errorMessageId = R.string.enter_full_data)
            return false
        }
        return true
    }
}