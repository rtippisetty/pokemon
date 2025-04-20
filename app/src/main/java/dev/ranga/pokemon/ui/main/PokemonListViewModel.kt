package dev.ranga.pokemon.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ranga.pokemon.api.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _pokemonList = MutableStateFlow(listOf<String>())
    val pokemonList: StateFlow<List<String>> = _pokemonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableSharedFlow<String?>()
    val error: SharedFlow<String?> = _error

    init {
        fetchPokemonList()
    }

    fun onNextPage() {
        val currentList = _pokemonList.value
        val nextPage = currentList.size
        fetchPokemonList(offset = nextPage)
    }

    private fun fetchPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val pokemons = getPokemonListUseCase.get(limit, offset)
                updatePokemonList(pokemons.names)
            } catch (e: Exception) {
                if(e is CancellationException) throw e
                onError(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun onError(message: String) {
            _error.emit(message)
    }

    private fun updatePokemonList(names: List<String>) {
        _pokemonList.update {
            it + names
        }
    }
}