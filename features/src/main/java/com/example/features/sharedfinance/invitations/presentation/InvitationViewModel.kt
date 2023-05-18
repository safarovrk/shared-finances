package com.example.features.sharedfinance.invitations.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetLoginUseCase
import com.example.core.utils.Resource
import com.example.core.utils.TextFieldState
import com.example.features.R
import com.example.features.sharedfinance.invitations.domain.ChoiceInvitationUseCase
import com.example.features.sharedfinance.invitations.domain.ChoiceRequest
import com.example.features.sharedfinance.invitations.domain.GetInvitationsUseCase
import com.example.features.sharedfinance.invitations.domain.InvitationsRequest
import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import com.example.features.sharedfinance.list_journals.presentation.JournalCreationDialogState
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsEvent
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsState
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationViewModel @Inject constructor(
    private val getInvitationsUseCase: GetInvitationsUseCase,
    private val choiceInvitationUseCase: ChoiceInvitationUseCase,
    private val getLoginUseCase: GetLoginUseCase,
) : ViewModel() {
    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
    }

    companion object {
        const val SUCCESS_CODE = 200
    }

    init {
        getInvitations()
    }

    private val _state = mutableStateOf(InvitationsState())
    val state: State<InvitationsState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: InvitationsEvent) {
        when (event) {
            is InvitationsEvent.OnAcceptClicked -> {
                viewModelScope.launch {
                    choiceInvitationUseCase(
                        choiceRequest = ChoiceRequest(
                            getLoginUseCase.invoke(),
                            event.journalName
                        ),
                        accepting = true
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == SUCCESS_CODE) {
                                    getInvitations()
                                } else {
                                    _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                                }
                            }

                            is Resource.Error -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                            }

                            is Resource.Loading -> {
                                _state.value = InvitationsState(isLoading = true)
                            }

                        }
                    }.launchIn(viewModelScope)
                }
            }

            is InvitationsEvent.OnDeclineClicked -> {
                viewModelScope.launch {
                    choiceInvitationUseCase(
                        choiceRequest = ChoiceRequest(
                            getLoginUseCase.invoke(),
                            event.journalName
                        ),
                        accepting = false
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == SUCCESS_CODE) {
                                    getInvitations()
                                } else {
                                    _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                                }
                            }

                            is Resource.Error -> {
                                _eventFlow.emit(UiEvent.ShowSnackBar(R.string.unexpected_error))
                            }

                            is Resource.Loading -> {
                                _state.value = InvitationsState(isLoading = true)
                            }

                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }


    fun getInvitations() {
        viewModelScope.launch {
            getInvitationsUseCase(
                InvitationsRequest(
                    InvitationsRequest.Login(getLoginUseCase.invoke())
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == SUCCESS_CODE) {
                            try {
                                if (result.data!!.body()?.isEmpty() == true) {
                                    _state.value = InvitationsState(
                                        errorMessageId = R.string.no_invitations_found
                                    )
                                } else {
                                    _state.value = InvitationsState(
                                        invitations = result.data!!.body() ?: emptyList()
                                    )
                                }
                            } catch (e: NullPointerException) {
                                if (_state.value.invitations.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                                else _state.value = InvitationsState(errorMessageId = R.string.unexpected_error)
                            }
                        } else {
                            if (_state.value.invitations.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                            else _state.value = InvitationsState(errorMessageId = R.string.unexpected_error)
                        }
                    }

                    is Resource.Error -> {
                        if (_state.value.invitations.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                        else _state.value = InvitationsState(errorMessageId = R.string.unexpected_error)

                    }

                    is Resource.Loading -> {
                        if (_state.value.invitations.isNotEmpty()) _state.value = InvitationsState(isRefreshing = true)
                        else _state.value = InvitationsState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}