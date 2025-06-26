package com.jean.cuidemonosaqp.modules.auth.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jean.cuidemonosaqp.modules.auth.data.repository.AuthRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AuthRepositoryImpl
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            repository.syncPendingRegistrations()
            //repository.syncNotes()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}