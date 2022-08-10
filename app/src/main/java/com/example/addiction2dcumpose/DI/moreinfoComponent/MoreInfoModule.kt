package com.example.addiction2dcumpose.DI.moreinfoComponent

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.repositories.MangaRepository
import com.example.addiction2dcumpose.repositories.MangaRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MoreInfoModule {

    @Provides
    fun provideMangaRepository(retrofitService: RetrofitService):MangaRepository {
        return MangaRepositoryImpl(retrofitService)
    }
}