package com.jean.cuidemonosaqp.modules.auth.data.sync

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jean.cuidemonosaqp.modules.auth.data.repository.AuthRepositoryImpl
import com.jean.cuidemonosaqp.shared.utils.ConnectionLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import android.widget.Toast
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch



class SyncManager @Inject constructor(
    private val repository: AuthRepositoryImpl,
    @ApplicationContext private val context: Context
) {

    private val connectionLiveData = ConnectionLiveData(context)

    fun startSyncListener(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner) { isConnected ->
            if (isConnected) {
                syncNow()
            }
        }
    }

    fun syncNow() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.syncPendingRegistrations()

            // Volvemos al hilo principal para mostrar el Toast
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Datos sincronizados correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun syncPeriodically() {
        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_work",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
