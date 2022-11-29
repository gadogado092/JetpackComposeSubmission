package com.example.playerapp.ui.screen.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playerapp.R
import com.example.playerapp.di.Injection
import com.example.playerapp.ui.ViewModelFactory
import com.example.playerapp.ui.common.UiState
import com.example.playerapp.ui.components.TextCenter
import com.example.playerapp.ui.theme.PlayerAppTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextCenter(text = stringResource(id = R.string.please_wait))
                viewModel.getAbout()
            }
            is UiState.Success -> {
                val data = uiState.data
                AboutContent(
                    name = data.name,
                    photoUrl = data.photoUrl,
                    email = data.email
                )
            }
            is UiState.Error -> {
                TextCenter(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun AboutContent(
    modifier: Modifier = Modifier,
    name: String,
    photoUrl: String,
    email: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        Column(
            modifier = modifier,
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                name, fontSize = 18.sp, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                email, modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutContentPreview() {
    PlayerAppTheme {
        AboutContent(
            name = "ada",
            photoUrl = "https://pbs.twimg.com/profile_images/439919384291598337/w_I1oArt_400x400.jpeg",
            email = "ada"
        )
    }
}
