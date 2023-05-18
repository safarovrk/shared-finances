package com.example.features.sharedfinance.home.categories.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetJournalUseCase
import com.example.core.shared_preferences.domain.GetLoginUseCase
import com.example.core.shared_preferences.domain.GetRoleUseCase
import com.example.core.utils.Resource
import com.example.core.utils.TextFieldState
import com.example.core.utils.UserRoleState
import com.example.features.R
import com.example.features.sharedfinance.home.categories.domain.CreateDeleteCategoryRequest
import com.example.features.sharedfinance.home.categories.domain.CreateDeleteCategoryUseCase
import com.example.features.sharedfinance.home.categories.domain.GetCategoriesUseCase
import com.example.features.sharedfinance.home.categories.domain.ListCategoryRequest
import com.example.features.sharedfinance.home.settings.presentation.InviteUserDialogState
import com.example.features.sharedfinance.invitations.presentation.InvitationViewModel
import com.example.features.sharedfinance.invitations.presentation.InvitationsState
import com.example.features.sharedfinance.list_journals.domain.CreateJournalRequest
import com.example.features.sharedfinance.list_journals.domain.JournalsRequest
import com.example.features.sharedfinance.list_journals.domain.RoleRequest
import com.example.features.sharedfinance.list_journals.presentation.JournalCreationDialogState
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsEvent
import com.example.features.sharedfinance.list_journals.presentation.ListJournalsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createDeleteCategoryUseCase: CreateDeleteCategoryUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getJournalUseCase: GetJournalUseCase,
    private val getLoginUseCase: GetLoginUseCase
) : ViewModel() {

    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
    }

    companion object {
        const val LOADED_SUCCESS_CODE = 200
        const val CREATED_SUCCESS_CODE = 201
        const val CATEGORY_EXIST_CODE = 409
        const val MAX_LETTER_COUNT = 30
    }


    private val _state = mutableStateOf(CategoriesState())
    val state: State<CategoriesState> = _state

    private val _dialogState = mutableStateOf(CategoryCreationDialogState())
    val dialogState: State<CategoryCreationDialogState> = _dialogState

    private val _categoryTextState = mutableStateOf(TextFieldState())
    val categoryTextState: State<TextFieldState> = _categoryTextState

    private val _userRoleState = mutableStateOf(UserRoleState())
    val userRoleState: State<UserRoleState> = _userRoleState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        /*viewModelScope.launch {
            runBlocking { getRole() }*/
        getRole()
        getCategories()


    }

    fun onEvent(event: CategoriesEvent) {
        when (event) {

            is CategoriesEvent.CreationClick -> {
                _state.value = CategoriesState(
                    categories = _state.value.categories,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isDialogShown = true
                )
            }

            is CategoriesEvent.OnCreationDismiss -> {
                _state.value = CategoriesState(
                    categories = _state.value.categories,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isDialogShown = false
                )
                _dialogState.value = CategoryCreationDialogState()
                _categoryTextState.value = TextFieldState()
            }

            is CategoriesEvent.OnCreationSubmit -> {
                createDeleteCategory(event.categoryName, true)
            }

            is CategoriesEvent.EnteredCategoryName -> {
                if (event.value.length <= MAX_LETTER_COUNT) {
                    _categoryTextState.value = categoryTextState.value.copy(
                        text = event.value
                    )
                }
            }

            is CategoriesEvent.OnDeleteClicked -> {
                createDeleteCategory(event.categoryName, false)
            }
        }
    }

    private fun createDeleteCategory(categoryName: String, creation: Boolean) {
        viewModelScope.launch {
            if (creation) if (!isFieldValid()) return@launch
            createDeleteCategoryUseCase(
                CreateDeleteCategoryRequest(
                    category = categoryName,
                    login = getLoginUseCase.invoke(),
                    journalName = getJournalUseCase.invoke()
                ),
                creation
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == CREATED_SUCCESS_CODE) {
                            onEvent(CategoriesEvent.OnCreationDismiss)
                            getCategories()

                        } else if (result.data?.code() == CATEGORY_EXIST_CODE) {
                            _dialogState.value = CategoryCreationDialogState(
                                errorMessageId = R.string.category_already_exist
                            )
                        }
                    }

                    is Resource.Error -> {
                        _dialogState.value = CategoryCreationDialogState(
                            errorMessageId = R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        _dialogState.value = CategoryCreationDialogState(
                            isLoading = true,
                            errorMessageId = _dialogState.value.errorMessageId
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    fun getCategories(/*isRefreshing: Boolean*/) {
        viewModelScope.launch {

            getCategoriesUseCase(
                ListCategoryRequest(getJournalUseCase.invoke())
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == LOADED_SUCCESS_CODE) {
                            try {
                                if (result.data!!.body()?.isEmpty() == true) {
                                    _state.value = CategoriesState(
                                        errorMessageId = R.string.no_categories_found
                                    )
                                } else {
                                    _state.value = CategoriesState(
                                        categories = result.data!!.body() ?: emptyList()
                                    )
                                }
                            } catch (e: NullPointerException) {
                                if (_state.value.categories.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                                else _state.value = CategoriesState(errorMessageId = R.string.unexpected_error)

                            }
                        } else {
                            if (_state.value.categories.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                            else _state.value = CategoriesState(errorMessageId = R.string.unexpected_error)
                        }
                    }

                    is Resource.Error -> {
                        if (_state.value.categories.isNotEmpty()) _eventFlow.emit(UiEvent.ShowSnackBar(messageId = R.string.unexpected_error))
                        else _state.value = CategoriesState(errorMessageId = R.string.unexpected_error)
                    }

                    is Resource.Loading -> {
                        if (_state.value.categories.isNotEmpty()) _state.value = CategoriesState(isRefreshing = true)
                        else _state.value = CategoriesState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getRole() {
        viewModelScope.launch {
            _userRoleState.value = UserRoleState(getRoleUseCase.invoke())
        }
    }

    private fun isFieldValid(): Boolean {

        val categoryName = _categoryTextState.value.text

        if (categoryName == "") {
            _dialogState.value = CategoryCreationDialogState(errorMessageId = R.string.enter_full_data)
            return false
        }
        return true
    }
}