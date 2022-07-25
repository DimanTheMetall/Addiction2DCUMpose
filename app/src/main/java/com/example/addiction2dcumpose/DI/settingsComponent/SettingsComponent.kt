package com.example.addiction2dcumpose.DI.settingsComponent

import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.DI.searchComponent.SearchComponent
import com.example.addiction2dcumpose.mvvm.settings.SettingsViewModel
import dagger.Component
import javax.inject.Scope

@SettingsScope
@Component(modules = [SettingsModule::class], dependencies = [AddictionComponent::class])
interface SettingsComponent {

    fun getViewModel():SettingsViewModel

    @Component.Factory
    interface Factory {
        fun create(addictionComponent: AddictionComponent): SettingsComponent
    }

}

@Scope
annotation class SettingsScope