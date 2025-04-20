package dev.ranga.pokemon.ui.common

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import dev.ranga.pokemon.ui.theme.PokemonTheme


@Composable
fun HDivider() {
    HorizontalDivider(
        thickness = PokemonTheme.dimensions.extraTiny,
        color = PokemonTheme.colorScheme.primary
    )
}