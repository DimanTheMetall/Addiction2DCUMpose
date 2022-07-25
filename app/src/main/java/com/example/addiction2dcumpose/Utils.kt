package com.example.addiction2dcumpose

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper

fun Application.getAsAddiction(): AddictionApp {
    return this as AddictionApp
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}