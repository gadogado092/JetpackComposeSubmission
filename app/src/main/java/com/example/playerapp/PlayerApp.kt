package com.example.playerapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.playerapp.ui.theme.PlayerAppTheme

@Composable
fun PlayerApp(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true)
@Composable
fun PlayerAppPreview() {
    PlayerAppTheme {
        PlayerApp()
    }
}