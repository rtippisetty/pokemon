package dev.ranga.pokemon.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import dev.ranga.pokemon.R
import dev.ranga.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonImageView(
    imageUrl: String,
    name: String,
    modifier: Modifier = Modifier
) {

    val painter = if (LocalInspectionMode.current) {
        painterResource(id = R.drawable.sample)
    } else {
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED) // Enable caching for better performance
                .networkCachePolicy(CachePolicy.READ_ONLY)
                .placeholder(R.drawable.place_image) // Placeholder before image loads
                .error(R.drawable.place_image) // Fallback image if loading fails
                .build(),
        )
    }
    Image(
        painter = painter,
        contentDescription = name,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun PokemonImagePreview() {
    PokemonTheme {
        PokemonImageView(
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            name = "Test image",
            modifier = Modifier.fillMaxSize()
        )
    }
}