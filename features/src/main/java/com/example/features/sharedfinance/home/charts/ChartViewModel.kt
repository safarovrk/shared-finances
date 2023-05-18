package com.example.features.sharedfinance.home.charts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.shared_preferences.domain.GetJournalUseCase
import com.example.core.utils.Resource
import com.example.features.R
import com.example.features.sharedfinance.home.categories.presentation.CategoriesState
import com.example.features.sharedfinance.home.journal.domain.GetNoteListUseCase
import com.example.features.sharedfinance.home.journal.domain.request_body.NoteListRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val getJournalUseCase: GetJournalUseCase
) : ViewModel() {


    sealed class UiEvent {
        data class ShowSnackBar(val messageId: Int) : UiEvent()
    }

    companion object {
        const val LOADED_SUCCESS_CODE = 200

    }

    private val _state = mutableStateOf(ChartState())
    val state: State<ChartState> = _state

    private val _chartDataState = mutableStateOf(ChartDataState())
    val chartDataState: State<ChartDataState> = _chartDataState


    init {
        getNotes()
    }

    fun getNotes() {

        viewModelScope.launch {
            getNoteListUseCase(
                NoteListRequest(getJournalUseCase.invoke())
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        when (result.data?.code()) {
                            LOADED_SUCCESS_CODE -> {
                                try {
                                    if (result.data!!.body()?.isEmpty() == true) {
                                        _state.value = ChartState(
                                            errorMessageId = R.string.no_data_for_chart
                                        )
                                    } else {
                                        _state.value = ChartState(
                                            notes = result.data!!.body() ?: emptyList()
                                        )
                                        loadChart()
                                    }
                                } catch (e: NullPointerException) {
                                    _state.value = ChartState(
                                        errorMessageId = R.string.unexpected_error
                                    )
                                }
                            }
                            else -> {
                                _state.value = ChartState(
                                    errorMessageId = R.string.no_notes_found
                                )
                            }
                        }

                    }

                    is Resource.Error -> {
                        _state.value = ChartState(errorMessageId = R.string.unexpected_error)

                    }

                    is Resource.Loading -> {
                        _state.value = ChartState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    private fun loadChart() {

        val categories: MutableMap<String, Int> = mutableMapOf()

        for (i in _state.value.notes.indices) {
            if (_state.value.notes[i].sum < 0) {
                if (!categories.containsKey(_state.value.notes[i].category.name)) {
                    categories[_state.value.notes[i].category.name] = 0
                }
                //var temp = categories[_state.value.notes[i].category.name]
                categories[_state.value.notes[i].category.name] =+ abs(_state.value.notes[i].sum).toInt()

            }
        }
        _chartDataState.value = ChartDataState(categories)
    }

}