package org.chrivin.hsrcomposeapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.chrivin.hsrcomposeapp.data.HSRCharacterRepo
import org.chrivin.hsrcomposeapp.model.HSRCharacter
import org.chrivin.hsrcomposeapp.ui.common.UiState

class HomeViewModel(
    private val repository: HSRCharacterRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<HSRCharacter>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<HSRCharacter>>>
        get() = _uiState

    private val _query = mutableStateOf("") //for searching
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchCharacter(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    suspend fun updateHSRChara(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateHSRChara(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}