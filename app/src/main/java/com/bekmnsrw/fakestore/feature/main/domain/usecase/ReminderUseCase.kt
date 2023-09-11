package com.bekmnsrw.fakestore.feature.main.domain.usecase

import com.bekmnsrw.fakestore.feature.main.domain.ReminderRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {

    suspend operator fun invoke(
        duration: Long,
        unit: TimeUnit,
        message: String
    ) = reminderRepository.scheduleCartReminder(
        duration = duration,
        unit = unit,
        message = message
    )
}
