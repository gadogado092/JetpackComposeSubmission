package com.example.playerapp.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.playerapp.ui.theme.GreyLight
import com.example.playerapp.ui.theme.PlayerAppTheme

@Composable
fun DetailScreen(
    playerId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                TextCenter(text = stringResource(id = R.string.please_wait))
                viewModel.getPlayerById(playerId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    name = data.name,
                    birthDate = data.birthDate,
                    tall = data.tall,
                    position = data.position,
                    photoUrl = data.photoUrl,
                    club = data.club,
                    country = data.country,
                    isFavourite = data.isFavourite
                )
            }
            is UiState.Error -> {
                TextCenter(text = stringResource(id = R.string.error_message, uiState.errorMessage))
            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    birthDate: String,
    tall: String,
    position: String,
    club: String,
    country: String,
    isFavourite: Boolean,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
//                    .clickable { onBackClick() }
            )
            if (isFavourite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = Color.Red,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
//                    .clickable { onBackClick() }
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
//                    .clickable { onBackClick() }
                )
            }
        }
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.CenterHorizontally)
                .background(GreyLight)
        )
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp),
        )
        Spacer(modifier = Modifier.height(4.dp))
        ItemDetail(title = "Tanggal Lahir", value = birthDate)
        ItemDetail(title = "Tinggi Badan", value = tall)
        ItemDetail(title = "Posisi", value = position)
        ItemDetail(title = "Club", value = club)
        ItemDetail(title = "Country", value = country)
    }
}

@Composable
fun ItemDetail(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 8.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
        )
        Text(
            text = value.ifEmpty {
                "-"
            },
            fontSize = 18.sp,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailContentPreview() {
    PlayerAppTheme {
        DetailContent(
            name = "Lionel Andrés Messi",
            birthDate = "20 Oktober 1992",
            tall = "170 cm",
            position = "Penyerang",
            club = "Paris Saint-Germain",
            country = "Argentina",
            isFavourite = true,
            photoUrl = ""
        )
    }
}