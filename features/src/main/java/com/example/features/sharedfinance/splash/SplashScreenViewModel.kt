package com.example.features.sharedfinance.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetJournalUseCase
import com.example.core.shared_preferences.domain.GetLoginUseCase
import com.example.core.shared_preferences.domain.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val getJournalUseCase: GetJournalUseCase,
) : ViewModel() {

    sealed class UiEvent {
        object Unauthorized : UiEvent()
        object AuthorizedWithNoJournal : UiEvent()
        object AuthorizedWithJournal : UiEvent()
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun checkAuthStatus() {
        viewModelScope.launch {
            if (getTokenUseCase.invoke().isEmpty() || getLoginUseCase.invoke().isEmpty()) {
                _eventFlow.emit(UiEvent.Unauthorized)
            } else if (getJournalUseCase.invoke().isEmpty()) {
                _eventFlow.emit(UiEvent.AuthorizedWithNoJournal)
            } else {
                _eventFlow.emit(UiEvent.AuthorizedWithJournal)
            }
        }
    }

}