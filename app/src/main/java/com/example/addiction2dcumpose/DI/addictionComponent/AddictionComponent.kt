package com.example.addiction2dcumpose.DI.addictionComponent

import android.content.Context
import com.example.addiction2dcumpose.RetrofitService.RetrofitService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AddictionComponent {

    fun provideRetrofitService(): RetrofitService

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AddictionComponent

    }

}