package dev.ranga.pokemon.data.remote

import dev.ranga.pokemon.data.remote.model.PokemonDetailsResponse
import dev.ranga.pokemon.data.remote.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDetailsResponse

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}