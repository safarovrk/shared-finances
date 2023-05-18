package com.example.features.sharedfinance.home.journal.presentation

import android.annotation.SuppressLint
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
import com.example.features.sharedfinance.home.categories.domain.GetCategoriesUseCase
import com.example.features.sharedfinance.home.categories.domain.ListCategoryRequest
import com.example.features.sharedfinance.home.journal.domain.CreateNoteUseCase
import com.example.features.sharedfinance.home.journal.domain.DeleteNoteUseCase
import com.example.features.sharedfinance.home.journal.domain.EditNoteUseCase
import com.example.features.sharedfinance.home.journal.domain.GetNoteListUseCase
import com.example.features.sharedfinance.home.journal.domain.request_body.CreateNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.DeleteNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.EditNoteRequest
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import com.example.features.sharedfinance.home.journal.presentation.create_dialog.CreateNoteDialogState
import com.example.features.sharedfinance.home.journal.presentation.edit_dialog.EditNoteDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val getLoginUseCase: GetLoginUseCase,
    private val getJournalUseCase: GetJournalUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
    }

    companion object {
        const val SUCCESS_CODE = 200
        const val INTENT_SUCCESS_CODE = 201
    }

    private val _state = mutableStateOf(JournalState())
    val state: State<JournalState> = _state

    private val _userRoleState = mutableStateOf(UserRoleState())
    val userRoleState: State<UserRoleState> = _userRoleState

    private val _dialogCreationState = mutableStateOf(CreateNoteDialogState())
    val dialogCreationState: State<CreateNoteDialogState> = _dialogCreationState

    private val _sumCreationTextState = mutableStateOf(TextFieldState())
    val sumCreationTextState: State<TextFieldState> = _sumCreationTextState
    private val _categoryCreationTextState = mutableStateOf(TextFieldState())
    val categoryCreationTextState: State<TextFieldState> = _categoryCreationTextState
    private val _commentCreationTextState = mutableStateOf(TextFieldState())
    val commentCreationTextState: State<TextFieldState> = _commentCreationTextState

    private val _dialogEditionState = mutableStateOf(EditNoteDialogState())
    val dialogEditionState: State<EditNoteDialogState> = _dialogEditionState

    private val _sumEditionTextState = mutableStateOf(TextFieldState())
    val sumEditionTextState: State<TextFieldState> = _sumEditionTextState
    private val _categoryEditionTextState = mutableStateOf(TextFieldState())
    val categoryEditionTextState: State<TextFieldState> = _categoryEditionTextState
    private val _commentEditionTextState = mutableStateOf(TextFieldState())
    val commentEditionTextState: State<TextFieldState> = _commentEditionTextState
    private val _idNoteState = mutableStateOf(TextFieldState())
    val idNoteState: State<TextFieldState> = _idNoteState
    private val _dateNoteState = mutableStateOf(TextFieldState())
    val dateNoteState: State<TextFieldState> = _dateNoteState

    private val _categoriesState = mutableStateOf(CategoryListState())
    val categoriesState: State<CategoryListState> = _categoriesState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getRole()
        getNotes()
    }

    fun onEvent(event: JournalEvent) {
        when (event) {


            is JournalEvent.EnteredSum -> {
                if (event.value.toIntOrNull() != null || event.value.isEmpty() || event.value == "-") {
                    _sumCreationTextState.value = sumCreationTextState.value.copy(
                        text = event.value
                    )
                }
            }

            is JournalEvent.EnteredCategory -> {
                _categoryCreationTextState.value = categoryCreationTextState.value.copy(
                    text = event.value
                )
            }

            is JournalEvent.EnteredComment -> {
                _commentCreationTextState.value = commentCreationTextState.value.copy(
                    text = event.value
                )
            }


            is JournalEvent.EditedSum -> {
                if (event.value.toIntOrNull() != null || event.value.isEmpty() || event.value == "-") {
                    _sumEditionTextState.value = sumEditionTextState.value.copy(
                        text = event.value
                    )
                }
            }

            is JournalEvent.EditedCategory -> {
                _categoryEditionTextState.value = categoryEditionTextState.value.copy(
                    text = event.value
                )
            }

            is JournalEvent.EditedComment -> {
                _commentEditionTextState.value = commentEditionTextState.value.copy(
                    text = event.value
                )
            }

            is JournalEvent.OnCreateClicked -> {

                viewModelScope.launch {
                    getCategoriesUseCase.invoke(
                        ListCategoryRequest(getJournalUseCase.invoke())
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == SUCCESS_CODE) {
                                    try {
                                        _categoriesState.value = CategoryListState(
                                            categories = result.data!!.body() ?: emptyList()
                                        )
                                    } catch (_: NullPointerException) {
                                    }
                                }
                            }

                            is Resource.Error -> {}

                            is Resource.Loading -> {}
                        }
                    }.launchIn(viewModelScope)

                    _state.value = JournalState(
                        notes = _state.value.notes,
                        errorMessageId = _state.value.errorMessageId,
                        isLoading = _state.value.isLoading,
                        isCreationDialogShown = true,
                        isEditionDialogShown = _state.value.isEditionDialogShown
                    )
                }

            }

            is JournalEvent.OnCreationDismiss -> {
                _state.value = JournalState(
                    notes = _state.value.notes,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isCreationDialogShown = false,
                    isEditionDialogShown = _state.value.isEditionDialogShown
                )
                _dialogCreationState.value = CreateNoteDialogState()

                _sumCreationTextState.value = TextFieldState()
                _categoryCreationTextState.value = TextFieldState()
                _commentCreationTextState.value = TextFieldState()
            }

            is JournalEvent.OnCreationSubmit -> {
                createNote(event.sum, event.category, event.comment)
            }

            is JournalEvent.OnEditClicked -> {

                viewModelScope.launch {
                    getCategoriesUseCase.invoke(
                        ListCategoryRequest(getJournalUseCase.invoke())
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if (result.data?.code() == SUCCESS_CODE) {
                                    try {
                                        _categoriesState.value = CategoryListState(
                                            categories = result.data!!.body() ?: emptyList()
                                        )
                                    } catch (_: NullPointerException) {
                                    }
                                }
                            }

                            is Resource.Error -> {}

                            is Resource.Loading -> {}
                        }
                    }.launchIn(viewModelScope)

                    _idNoteState.value = TextFieldState(event.note.id.toString())
                    _dateNoteState.value = TextFieldState(event.note.date)
                    _sumEditionTextState.value = TextFieldState(event.note.sum.toString())
                    _categoryEditionTextState.value = TextFieldState(event.note.category.name)
                    _commentEditionTextState.value = TextFieldState(event.note.comment)

                    _state.value = JournalState(
                        notes = _state.value.notes,
                        errorMessageId = _state.value.errorMessageId,
                        isLoading = _state.value.isLoading,
                        isCreationDialogShown = _state.value.isCreationDialogShown,
                        isEditionDialogShown = true
                    )
                }
            }

            is JournalEvent.OnEditionDismiss -> {
                _state.value = JournalState(
                    notes = _state.value.notes,
                    errorMessageId = _state.value.errorMessageId,
                    isLoading = _state.value.isLoading,
                    isCreationDialogShown = _state.value.isCreationDialogShown,
                    isEditionDialogShown = false
                )
                _dialogEditionState.value = EditNoteDialogState()

                _idNoteState.value = TextFieldState()
                _dateNoteState.value = TextFieldState()
                _sumEditionTextState.value = TextFieldState()
                _categoryEditionTextState.value = TextFieldState()
                _commentEditionTextState.value = TextFieldState()
            }

            is JournalEvent.OnEditionSubmit -> {
                editNote(event.id, event.date, event.sum, event.category, event.comment)
            }

            is JournalEvent.OnDeleteClicked -> {
                deleteNote(event.idNote)
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            getNoteListUseCase(
                NoteListRequest(getJournalUseCase.invoke())
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        when (result.data?.code()) {
                            SUCCESS_CODE -> {
                                try {
                                    if (result.data!!.body()?.isEmpty() == true) {
                                        _state.value = JournalState(
                                            errorMessageId = R.string.no_notes_found
                                        )
                                    } else {
                                        _state.value = JournalState(
                                            notes = result.data!!.body() ?: emptyList()
                                        )
                                    }

                                } catch (e: NullPointerException) {
                                    if (_state.value.notes.isNotEmpty()) _eventFlow.emit(
                                        UiEvent.ShowSnackBar(
                                            messageId = R.string.unexpected_error
                                        )
                                    )
                                    else _state.value =
                                        JournalState(errorMessageId = R.string.unexpected_error)
                                }
                            }

                            else -> {
                                if (_state.value.notes.isNotEmpty()) _eventFlow.emit(
                                    UiEvent.ShowSnackBar(
                                        messageId = R.string.unexpected_error
                                    )
                                )
                                else _state.value =
                                    JournalState(errorMessageId = R.string.unexpected_error)
                            }
                        }
                    }

                    is Resource.Error -> {
                        if (_state.value.notes.isNotEmpty()) _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                messageId = R.string.unexpected_error
                            )
                        )
                        else _state.value = JournalState(errorMessageId = R.string.unexpected_error)
                    }

                    is Resource.Loading -> {
                        if (_state.value.notes.isNotEmpty()) _state.value =
                            JournalState(isRefreshing = true)
                        else _state.value = JournalState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun createNote(sum: String, category: String, comment: String) {
        viewModelScope.launch {

            if (!isCreationFieldsValid()) return@launch

            createNoteUseCase(
                CreateNoteRequest(
                    getDate(),
                    sum.toLong(),
                    category,
                    comment,
                    getJournalUseCase.invoke()
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == INTENT_SUCCESS_CODE) {
                            onEvent(JournalEvent.OnCreationDismiss)
                            getNotes()

                        } else {
                            _dialogCreationState.value = CreateNoteDialogState(
                                errorMessageId = R.string.unexpected_error
                            )
                        }
                    }

                    is Resource.Error -> {
                        _dialogCreationState.value = CreateNoteDialogState(
                            errorMessageId = R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        _dialogCreationState.value = CreateNoteDialogState(
                            isLoading = true,
                            errorMessageId = _dialogCreationState.value.errorMessageId
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun editNote(id: Long, date: String, sum: String, category: String, comment: String) {
        viewModelScope.launch {

            if (!isEditionFieldsValid()) return@launch

            editNoteUseCase(
                EditNoteRequest(
                    id = id,
                    date = date,
                    sum = sum.toLong(),
                    category = category,
                    comment = comment,
                    login = getLoginUseCase.invoke()
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == SUCCESS_CODE) {
                            onEvent(JournalEvent.OnEditionDismiss)
                            getNotes()

                        } else {
                            _dialogEditionState.value = EditNoteDialogState(
                                errorMessageId = R.string.unexpected_error
                            )
                        }
                    }

                    is Resource.Error -> {
                        _dialogEditionState.value = EditNoteDialogState(
                            errorMessageId = R.string.unexpected_error
                        )
                    }

                    is Resource.Loading -> {
                        _dialogEditionState.value = EditNoteDialogState(
                            isLoading = true,
                            errorMessageId = _dialogEditionState.value.errorMessageId
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(
                DeleteNoteRequest(
                    idNote = id,
                    login = getLoginUseCase.invoke(),
                    journalName = getJournalUseCase.invoke()
                )
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data?.code() == INTENT_SUCCESS_CODE) {
                            getNotes()
                        }
                        _state.value = JournalState(isLoading = false)
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                messageId = R.string.no_notes_found
                            )
                        )
                        _state.value = JournalState(isLoading = false)
                    }

                    is Resource.Loading -> {
                        _state.value = JournalState(isLoading = true)
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {
        val c = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val hour: Int = c.get(Calendar.HOUR)
        val minute: Int = c.get(Calendar.MINUTE)
        val second: Int = c.get(Calendar.SECOND)
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = month
        cal[Calendar.DAY_OF_MONTH] = day
        cal[Calendar.HOUR] = hour
        cal[Calendar.MINUTE] = minute
        cal[Calendar.SECOND] = second
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        return format.format(cal.time).toString()
    }

    private fun getRole() {
        viewModelScope.launch {
            _userRoleState.value = UserRoleState(getRoleUseCase.invoke())
        }
    }

    private fun isCreationFieldsValid(): Boolean {

        val sum = _sumCreationTextState.value.text
        val comment = _commentCreationTextState.value.text

        if (sum == "") {
            _dialogCreationState.value =
                CreateNoteDialogState(errorMessageId = R.string.enter_full_data)
            return false
        }
        if (sum.toLong() > 1000000000) {
            _dialogCreationState.value =
                CreateNoteDialogState(errorMessageId = R.string.too_long_sum)
            return false
        }
        if (comment.length > 500) {
            _dialogCreationState.value =
                CreateNoteDialogState(errorMessageId = R.string.comment_more_500)
            return false
        }
        return true
    }

    private fun isEditionFieldsValid(): Boolean {

        val sum = _sumEditionTextState.value.text
        val comment = _commentEditionTextState.value.text

        if (sum == "") {
            _dialogEditionState.value =
                EditNoteDialogState(errorMessageId = R.string.enter_full_data)
            return false
        }
        if (sum.toLong() > 1000000000) {
            _dialogEditionState.value = EditNoteDialogState(errorMessageId = R.string.too_long_sum)
            return false
        }
        if (comment.length > 500) {
            _dialogEditionState.value =
                EditNoteDialogState(errorMessageId = R.string.comment_more_500)
            return false
        }
        return true
    }

    fun String.isNumeric(): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return this.matches(regex)
    }

}