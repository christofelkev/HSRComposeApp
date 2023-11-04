package org.chrivin.hsrcomposeapp.ui.viewmodel

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

class FavoriteViewModel(
    private val repository: HSRCharacterRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<HSRCharacter>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<HSRCharacter>>>
        get() = _uiState

    fun getFavoriteHSRChara() = viewModelScope.launch {
        repository.getFavoriteHSRChara()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    suspend fun updateHSRChara(id: Int, newState: Boolean) {
        repository.updateHSRChara(id, newState)
        getFavoriteHSRChara()
    }
}