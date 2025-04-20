package dev.ranga.pokemon.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ranga.pokemon.R
import dev.ranga.pokemon.ui.common.HDivider
import dev.ranga.pokemon.ui.common.LoadingView
import dev.ranga.pokemon.ui.common.PokemonAppBar
import dev.ranga.pokemon.ui.common.PokemonImageView
import dev.ranga.pokemon.ui.detail.model.PokemonDetailsState
import dev.ranga.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonDetailsScreen(onBack: () -> Unit) {
    val viewModel: PokemonDetailsViewModel = hiltViewModel()
    val state = viewModel.pokemonDetails.collectAsState()
    PokemonDetailsContent(
        state = state.value,
        onBack = onBack
    )
}

@Composable
fun PokemonDetailsContent(
    state: PokemonDetailsState,
    onBack: () -> Unit
) {
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

            when (val value = state) {
                PokemonDetailsState.Loading -> {
                    LoadingView(
                        modifier = Modifier
                            .testTag("LoadingView")
                            .padding(PokemonTheme.dimensions.large)
                            .fillMaxWidth()
                    )
                }

                is PokemonDetailsState.Error -> {
                    ErrorView(
                        message = value.message,
                        modifier = Modifier.testTag("ErrorView")
                    )
                }

                is PokemonDetailsState.Success -> {
                    PokemonDetailsView(
                        modifier = Modifier.testTag("PokemonDetailsView"),
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
fun PokemonDetailsView(
    modifier: Modifier = Modifier,
    name: String,
    height: Int,
    imageUrl: String?,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HDivider()
        Text(
            text = "name: $name",
            modifier = Modifier
                .padding(PokemonTheme.dimensions.large)
                .fillMaxWidth(),
            style = TextStyle(
                color = Color.Black,
                fontSize = PokemonTheme.dimensions.fontSizeLarge,
                fontWeight = FontWeight.Medium
            )
        )
        HDivider()
        Text(
            text = "height: $height",
            modifier = Modifier
                .padding(PokemonTheme.dimensions.large)
                .fillMaxWidth(),
        )
        HDivider()
        imageUrl?.let {
            PokemonImageView(
                imageUrl = it,
                name = name,
                modifier = Modifier
                    .testTag("PokemonImageView")
                    .size(PokemonTheme.dimensions.xxxxLarge)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun ErrorView(message: String, modifier: Modifier = Modifier) {
    Text(
        text = message,
        modifier = modifier
            .padding(PokemonTheme.dimensions.large)
            .fillMaxWidth(),
        style = TextStyle(
            color = Color.Red,
            fontSize = PokemonTheme.dimensions.fontSizeLarge,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.W800,
            fontStyle = FontStyle.Italic,
            letterSpacing = 0.5.em,
            background = Color.LightGray,
            textDecoration = TextDecoration.Underline
        )
    )
}