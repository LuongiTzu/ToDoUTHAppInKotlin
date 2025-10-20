package com.example.todoapp.feature.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.domain.onboarding.model.OnboardingItem
import com.example.todoapp.ui.theme.ToDoAppTheme

@Composable
fun OnboardingPage(item: OnboardingItem) {
    Column(
        Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(item.imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(220.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(Modifier.height(24.dp))
        Text(item.title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text(item.body, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
private fun PagePreview() = ToDoAppTheme {
    OnboardingPage(
        OnboardingItem(
            "Easy Time Management",
            "With management based on priority and daily tasks...",
            R.drawable.dao1
        )
    )
}
