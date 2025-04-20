package dev.ranga.pokemon.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranga.pokemon.api.GetPokemonDetailsUseCase
import dev.ranga.pokemon.api.model.PokemonDetails
import dev.ranga.pokemon.ui.detail.model.PokemonDetailsState
import dev.ranga.pokemon.ui.detail.model.UIPokemonDetails
import dev.ranga.pokemon.ui.navigation.PokemonDetailsDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
) : ViewModel() {
    private val name: String? = savedStateHandle[PokemonDetailsDestination::name.name]

    private val _pokemonDetails = MutableStateFlow<PokemonDetailsState>(PokemonDetailsState.Loading)
    val pokemonDetails: StateFlow<PokemonDetailsState> = _pokemonDetails

    init {
        fetchPokemonDetails(name)
    }

    private fun fetchPokemonDetails(name: String?) {
        if (name == null) {
            onError("Invalid pokemon name")
            return
        }
        viewModelScope.launch {
            _pokemonDetails.value = PokemonDetailsState.Loading
            try {
                val pokemonDetails = getPokemonDetailsUseCase.get(name)
                onSuccess(pokemonDetails)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                onError(e.message ?: "Unknown error")
            }
        }
    }

    private fun onSuccess(pokemonDetails: PokemonDetails) {
        _pokemonDetails.value = PokemonDetailsState.Success(
            UIPokemonDetails(
                name = pokemonDetails.name,
                imageUrl = pokemonDetails.spriteImageUrl,
                height = pokemonDetails.height
            )
        )
    }

    private fun onError(message: String) {
        _pokemonDetails.value = PokemonDetailsState.Error(message)
    }
}