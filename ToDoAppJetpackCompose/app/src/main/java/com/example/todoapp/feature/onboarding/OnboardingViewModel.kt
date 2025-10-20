package com.example.todoapp.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.onboarding.OnboardingRepositoryFake
import com.example.todoapp.domain.onboarding.model.OnboardingItem
import com.example.todoapp.domain.onboarding.usecase.GetOnboardingPagesUseCase
import com.example.todoapp.domain.onboarding.usecase.MarkOnboardingSeenUseCase

/**
 * ViewModel gói logic: cung cấp pages và đánh dấu đã xem.
 * Tạo factory tại chỗ để không cần DI ở giai đoạn này.
 */
class OnboardingViewModel(
    private val getPages: GetOnboardingPagesUseCase,
    private val markSeen: MarkOnboardingSeenUseCase
) : ViewModel() {

    val pages: List<OnboardingItem> = getPages()

    fun onFinished() = markSeen()

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repo = OnboardingRepositoryFake()
                val get = GetOnboardingPagesUseCase(repo)
                val mark = MarkOnboardingSeenUseCase(repo)
                return OnboardingViewModel(get, mark) as T
            }
        }
    }
}
