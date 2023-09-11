package com.bekmnsrw.fakestore.feature.main.data

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bekmnsrw.fakestore.feature.main.domain.ReminderRepository
import com.bekmnsrw.fakestore.feature.main.worker.ReminderWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    context: Context
) : ReminderRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleCartReminder(duration: Long, unit: TimeUnit, message: String) {
        val data = Data.Builder().putString(ReminderWorker.nameKey, message)

        val workRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            message + duration,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )
    }
}
