package com.jean.cuidemonosaqp.app
import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import javax.inject.Inject


@HiltAndroidApp
class CuidemonosAQPApp  : Application(), Configuration.Provider {

    // Hilt inyecta esta fábrica para crear Workers
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // Esta función es requerida para usar WorkManager con Hilt
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}