package dev.ranga.pokemon.ui.detail

import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.ui.detail.model.UIPokemonDetails
import javax.inject.Inject

class UIPokemonDetailsMapper @Inject constructor() {
    fun map(pokemonDetails: PokemonDetails): UIPokemonDetails {
        return UIPokemonDetails(
            name = pokemonDetails.name,
            imageUrl = pokemonDetails.spriteImageUrl,
            height = pokemonDetails.height
        )
    }
}