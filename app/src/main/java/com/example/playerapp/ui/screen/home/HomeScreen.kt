package com.example.playerapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playerapp.R
import com.example.playerapp.di.Injection
import com.example.playerapp.model.Player
import com.example.playerapp.ui.ViewModelFactory
import com.example.playerapp.ui.common.UiState
import com.example.playerapp.ui.components.PlayerItem
import com.example.playerapp.ui.components.TextCenter

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextCenter(text = stringResource(id = R.string.please_wait))
                viewModel.getAllPlayers()
            }
            is UiState.Success -> {
                HomeContent(
                    players = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {
                TextCenter(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun HomeContent(
    players: List<Player>,
    modifier: Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(players) { data ->
            PlayerItem(
                name = data.name,
                photoUrl = data.photoUrl,
                data.position, data.country,
                modifier = Modifier.clickable {
                    navigateToDetail(data.id)
                }
            )
        }
    }
}
