package com.example.addiction2dcumpose.DI.settingsComponent

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.repositories.GenreRepository
import com.example.addiction2dcumpose.repositories.GenresRepositoryImpl
import com.example.addiction2dcumpose.repositories.MangaRepository
import com.example.addiction2dcumpose.repositories.MangaRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideGenreRepository(retrofitService: RetrofitService): GenreRepository {
        return GenresRepositoryImpl(retrofitService)
    }
}