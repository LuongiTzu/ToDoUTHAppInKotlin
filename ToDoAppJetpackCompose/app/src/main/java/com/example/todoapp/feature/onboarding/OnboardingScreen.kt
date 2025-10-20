package com.example.todoapp.feature.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.feature.onboarding.component.BackCircleButton
import com.example.todoapp.feature.onboarding.component.DotsIndicator
import com.example.todoapp.feature.onboarding.component.OnboardingPage
import com.example.todoapp.ui.theme.ToDoAppTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinished: () -> Unit
) {
    val vm: OnboardingViewModel = viewModel(factory = OnboardingViewModel.provideFactory())
    val pages = vm.pages

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DotsIndicator(total = pages.size, current = pagerState.currentPage)
            Text(
                text = "skip",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    vm.onFinished()
                    onFinished()
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            OnboardingPage(item = pages[page])
        }

        val isFirst = pagerState.currentPage == 0
        val isLast = pagerState.currentPage == pages.lastIndex

        // --- Thanh dưới: nút tròn Back + nút Next/Get Started ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chỉ hiện nút Back khi không ở trang đầu
            if (!isFirst) {
                BackCircleButton(
                    enabled = true, 
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                )
            }

            Spacer(Modifier.width(10.dp))

            Button(
                onClick = {
                    if (isLast) {
                        vm.onFinished()
                        onFinished()
                    } else scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(1f),    // nút dài như mockup
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(if (isLast) "Get Started" else "Next")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OnboardingPreview() = ToDoAppTheme { OnboardingScreen {} }
