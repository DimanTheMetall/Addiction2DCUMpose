package com.example.addiction2dcumpose.DI.randomComponent

import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.data.database.AddictionDataBase
import com.example.addiction2dcumpose.repositories.MangaRepository
import com.example.addiction2dcumpose.repositories.MangaRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RandomModule {

    @Provides
    fun provideMangaRepository(
        retrofitService: RetrofitService,
        dataBase: AddictionDataBase
    ): MangaRepository {
        return MangaRepositoryImpl(retrofitService = retrofitService, dataBase = dataBase)
    }


}