package org.chrivin.hsrcomposeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.chrivin.hsrcomposeapp.data.HSRCharacterRepo
import org.chrivin.hsrcomposeapp.model.HSRCharacter
import org.chrivin.hsrcomposeapp.ui.common.UiState

class DetailViewModel(
    private val repository: HSRCharacterRepo
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<HSRCharacter>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<HSRCharacter>>
        get() = _uiState

    private fun setLoadingState() {
        _uiState.value = UiState.Loading
    }

    fun getHSRCharaById(id: Int) = viewModelScope.launch {
        setLoadingState()
        _uiState.value = UiState.Success(repository.getHSRCharaById(id))
    }

    fun updateHSRChara(id: Int, newState: Boolean) = viewModelScope.launch {
        setLoadingState()
        repository.updateHSRChara(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getHSRCharaById(id)
            }
    }
}