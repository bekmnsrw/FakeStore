package com.bekmnsrw.fakestore.feature.main.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val nameKey = "NAME"
        private const val REMINDER_ERROR_TAG = "REMINDER_ERROR"
    }

    override suspend fun doWork(): Result {
        val message = inputData.getString(nameKey)
        makeCartReminderNotification(message!!, applicationContext)

        return withContext(Dispatchers.IO) {
            return@withContext try {
                Result.success()
            } catch (throwable: Throwable) {
                Log.e(REMINDER_ERROR_TAG, "$throwable")
                Result.failure()
            }
        }
    }
}
