package com.example.addiction2dcumpose.DI.addictionComponent

import android.content.Context
import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import com.example.addiction2dcumpose.data.database.AddictionDataBase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, RoomModule::class])
interface AddictionComponent {

    fun provideRetrofitService(): RetrofitService

    fun provideDataBase(): AddictionDataBase

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AddictionComponent

    }

}