package dev.ranga.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.ranga.pokemon.ui.detail.PokemonDetailsScreen
import dev.ranga.pokemon.ui.main.PokemonListScreen
import kotlinx.serialization.Serializable

@Serializable
object PokemonList

@Serializable
data class PokemonDetails(
    val name: String
)

private fun NavGraphBuilder.pokemonListScreen(
    navigateToPokemonDetails: (name: String) -> Unit
) {
    composable<PokemonList> {
        PokemonListScreen(navigateToPokemonDetails)
    }
}

private fun NavGraphBuilder.pokemonDetailsScreen(
    navigateToPokemonList: () -> Unit
) {
    composable<PokemonDetails> {
        PokemonDetailsScreen(navigateToPokemonList)
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PokemonList
    ) {
        pokemonListScreen(
            navigateToPokemonDetails = { name ->
                navController.navigate(PokemonDetails(name))
            }
        )

        pokemonDetailsScreen(
            navigateToPokemonList = {
                navController.navigate(PokemonList)
            }
        )
    }
}
