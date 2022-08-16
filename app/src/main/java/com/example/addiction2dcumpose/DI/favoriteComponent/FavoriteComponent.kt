package com.example.addiction2dcumpose.DI.favoriteComponent

import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.DI.searchComponent.MangaModule
import com.example.addiction2dcumpose.mvvm.favorites.MangaFavoriteViewModel
import dagger.Component
import javax.inject.Scope

@FavoriteScope
@Component(modules = [MangaModule::class], dependencies = [AddictionComponent::class])
interface FavoriteComponent {

    fun getViewModel(): MangaFavoriteViewModel

    @Component.Factory
    interface Factory {
        fun create(addictionComponent: AddictionComponent): FavoriteComponent
    }

}

@Scope
annotation class FavoriteScope