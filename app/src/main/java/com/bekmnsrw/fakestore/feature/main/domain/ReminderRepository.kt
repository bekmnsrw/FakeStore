package com.bekmnsrw.fakestore.feature.main.domain

import java.util.concurrent.TimeUnit

interface ReminderRepository {

    suspend fun scheduleCartReminder(duration: Long, unit: TimeUnit, message: String)
}
