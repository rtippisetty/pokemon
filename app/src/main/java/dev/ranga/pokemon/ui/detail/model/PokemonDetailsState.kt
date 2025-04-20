package dev.ranga.pokemon.ui.detail.model

sealed interface PokemonDetailsState {
    object Loading : PokemonDetailsState
    data class Success(val pokemonDetails: UIPokemonDetails) : PokemonDetailsState
    data class Error(val message: String) : PokemonDetailsState
}