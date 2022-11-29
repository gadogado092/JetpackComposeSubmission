package com.example.playerapp.ui.screen.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerapp.data.PlayerRepository
import com.example.playerapp.model.About
import com.example.playerapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AboutViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<About>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<About>>
        get() = _uiState

    fun getAbout() {
        viewModelScope.launch {
            repository.getAbout()
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAbout())
        }
    }

}