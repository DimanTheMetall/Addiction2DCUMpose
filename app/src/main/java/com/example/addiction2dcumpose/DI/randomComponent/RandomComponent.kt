package com.example.addiction2dcumpose.DI.randomComponent

import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.mvvm.random.RandomViewModel
import dagger.Component
import javax.inject.Scope

@RandomScope
@Component(modules = [RandomModule::class], dependencies = [AddictionComponent::class])
interface RandomComponent {

    fun getViewModel(): RandomViewModel


    @Component.Factory
    interface Factory {
        fun create(addictionComponent: AddictionComponent): RandomComponent
    }
}

@Scope
annotation class RandomScope