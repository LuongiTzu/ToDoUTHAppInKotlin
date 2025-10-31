package com.example.todoapp.data.onboarding

import com.example.todoapp.R
import com.example.todoapp.domain.onboarding.OnboardingRepository
import com.example.todoapp.domain.onboarding.model.OnboardingItem

/**
 * Repo giả cho Onboarding – đủ dùng để chạy flow.
 * Sau này bạn thay bằng Impl dùng DataStore/RemoteConfig mà không cần đổi UI.
 */
class OnboardingRepositoryFake : OnboardingRepository {

    private var seen = false

    override fun getPages(): List<OnboardingItem> = listOf(
        OnboardingItem(
            title = "Easy Time Management",
            body = "With management based on priority and daily tasks, it gives you convenience in managing and deciding what must be done first.",
            imageRes = R.drawable.anh1
        ),
        OnboardingItem(
            title = "Increase Work Effectiveness",
            body = "Time management and determining the more important tasks will give your job better statistics and improvement.",
            imageRes = R.drawable.anh2
        ),
        OnboardingItem(
            title = "Reminder Notification",
            body = "This app provides reminders so you don’t forget your assignments according to the time you set.",
            imageRes = R.drawable.anh3
        )
    )

    override fun markSeen() { seen = true }

    override fun isSeen(): Boolean = seen
}
