package com.example.addiction2dcumpose

import android.app.Application
import com.example.addiction2dcumpose.DI.addictionComponent.AddictionComponent
import com.example.addiction2dcumpose.DI.addictionComponent.DaggerAddictionComponent

class AddictionApp : Application() {

    lateinit var addictionComponent: AddictionComponent

    override fun onCreate() {
        super.onCreate()

        addictionComponent = DaggerAddictionComponent.factory().create(applicationContext)

    }
}