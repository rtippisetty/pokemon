package dev.ranga.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.ranga.pokemon.api.GetPokemonDetailsUseCase
import dev.ranga.pokemon.api.GetPokemonListUseCase
import dev.ranga.pokemon.domain.GetPokemonDetailsUseCaseImpl
import dev.ranga.pokemon.domain.GetPokemonListUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface PokemonDomainModule {

    @Binds
    fun bindGetPokemonsUseCase(impl: GetPokemonListUseCaseImpl): GetPokemonListUseCase

    @Binds
    fun bindGetPokemonDetailsUseCase(impl: GetPokemonDetailsUseCaseImpl): GetPokemonDetailsUseCase

}