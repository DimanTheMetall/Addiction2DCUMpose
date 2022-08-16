package com.example.addiction2dcumpose.DI.searchComponent


import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.mvvm.search.SearchViewModel
import dagger.Component
import javax.inject.Scope

@SearchScope
@Component(modules = [MangaModule::class], dependencies = [AddictionComponent::class])
interface SearchComponent {

    fun getViewModel(): SearchViewModel

    @Component.Factory
    interface Factory {
        fun create(addictionComponent: AddictionComponent): SearchComponent
    }

}

@Scope
annotation class SearchScope