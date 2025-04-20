package dev.ranga.pokemon.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ranga.pokemon.R
import dev.ranga.pokemon.ui.common.LoadingView
import dev.ranga.pokemon.ui.common.PokemonAppBar
import dev.ranga.pokemon.ui.common.PokemonImageView
import dev.ranga.pokemon.ui.detail.model.PokemonDetailsState

@Composable
fun PokemonDetailsScreen(onBack: () -> Unit) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val state = viewModel.pokemonDetails.collectAsState()
    Scaffold(
        topBar = {
            PokemonAppBar(
                title = stringResource(id = R.string.pokemon_details),
                onBackEnabled = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            when (val value = state.value) {
                PokemonDetailsState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxWidth())
                }

                is PokemonDetailsState.Error -> {
                    ErrorScreen(value.message)
                }

                is PokemonDetailsState.Success -> {
                    PokemonDetailsScreenContent(
                        name = value.pokemonDetails.name,
                        height = value.pokemonDetails.height,
                        imageUrl = value.pokemonDetails.imageUrl
                    )
                }
            }
        }
    }
}

@Composable
private fun PokemonDetailsScreenContent(
    name: String,
    height: Int,
    imageUrl: String?,
) {
    Column {
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Text(
            text = "name: $name",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Text(
            text = "height: $height",
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        imageUrl?.let {
            PokemonImageView(
                imageUrl = it,
                name = name,
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun ErrorScreen(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        style = TextStyle(
            color = Color.Red,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.W800,
            fontStyle = FontStyle.Italic,
            letterSpacing = 0.5.em,
            background = Color.LightGray,
            textDecoration = TextDecoration.Underline
        )
    )
}