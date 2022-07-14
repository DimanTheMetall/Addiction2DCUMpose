package com.example.addiction2dcumpose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.addiction2dcumpose.repositories.MangaRepository

class ViewModelFactory(private val mangaRepository: MangaRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}