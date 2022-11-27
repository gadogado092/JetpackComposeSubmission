package com.example.playerapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playerapp.data.PlayerRepository
import com.example.playerapp.model.Player
import com.example.playerapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Player>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Player>>>
        get() = _uiState

    fun getAllPlayers() {
        viewModelScope.launch {
            repository.getAllPlayer()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { players ->
                    _uiState.value = UiState.Success(players)
                }
        }
    }

}