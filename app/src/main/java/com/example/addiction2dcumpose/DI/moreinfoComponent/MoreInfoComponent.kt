package com.example.addiction2dcumpose.DI.moreinfoComponent

import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.DI.searchComponent.MangaModule
import com.example.addiction2dcumpose.mvvm.moreinfo.MoreInfoViewModel
import dagger.Component
import javax.inject.Scope

@MoreInfoScope
@Component(modules = [MangaModule::class], dependencies = [AddictionComponent::class])
interface MoreInfoComponent {

    fun getViewModel():MoreInfoViewModel

    @Component.Factory
    interface Factory {
        fun create(addictionComponent: AddictionComponent): MoreInfoComponent
    }

}

@Scope
annotation class MoreInfoScope