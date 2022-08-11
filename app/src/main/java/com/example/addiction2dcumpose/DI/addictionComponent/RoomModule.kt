package com.example.addiction2dcumpose.DI.addictionComponent

import android.content.Context
import androidx.room.Room
import com.example.addiction2dcumpose.Constants
import com.example.addiction2dcumpose.data.database.AddictionDataBase
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @Provides
    fun provideDataBase(context: Context): AddictionDataBase {
        return Room.databaseBuilder(context, AddictionDataBase::class.java, Constants.DB_NAME)
            .build()
    }
}