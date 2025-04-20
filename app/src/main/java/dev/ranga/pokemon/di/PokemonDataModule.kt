package dev.ranga.pokemon.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ranga.pokemon.BuildConfig
import dev.ranga.pokemon.data.PokemonRepositoryImpl
import dev.ranga.pokemon.data.mapper.DomainModelMapper
import dev.ranga.pokemon.data.remote.PokemonService
import dev.ranga.pokemon.domain.PokemonRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDataModule {
    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonService: PokemonService,
        domainModelMapper: DomainModelMapper
    ): PokemonRepository {
        return PokemonRepositoryImpl(pokemonService, domainModelMapper)
    }

    @Provides
    @Singleton
    fun provideRecipeService(client: OkHttpClient): PokemonService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PokemonService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            if (BuildConfig.DEBUG) {
                Log.d("PokemonService", "Request: ${request.url}")
            }
            chain.proceed(request)
        }
    }
}