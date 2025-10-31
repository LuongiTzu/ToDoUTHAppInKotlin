package com.example.todoapp.feature.auth.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.R
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onSignedIn: () -> Unit
) {
    val vm: LoginViewModel = viewModel()
    val state by vm.state.collectAsState()

    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as Activity

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                vm.signInWithGoogleIdToken(
                    idToken = idToken,
                    onSuccess = {
                        // đảm bảo user đã sync tên / email / avatar
                        FirebaseAuth.getInstance().currentUser?.reload()?.addOnCompleteListener {
                            scope.launch {
                                snack.showSnackbar("Đăng nhập thành công")
                                onSignedIn()
                            }
                        }
                    },
                    onError = { msg -> scope.launch { snack.showSnackbar(msg) } }
                )
            } else {
                scope.launch { snack.showSnackbar("Không lấy được idToken") }
            }
        } catch (e: ApiException) {
            scope.launch { snack.showSnackbar("Google Sign-In lỗi: ${e.statusCode}") }
        }
    }

    fun launchGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(activity, gso)

        // luôn hiện account picker
        client.signOut().addOnCompleteListener {
            googleLauncher.launch(client.signInIntent)
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snack) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            // ===== NỬA TRÊN =====
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f).offset(y = (-60).dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(R.drawable.nenlogin),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.uthlogin),
                        contentDescription = null,
                        modifier = Modifier.height(160.dp).fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "SmartTasks",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "A simple and efficient to-do app",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }

            // ===== NỬA DƯỚI =====
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.height(60.dp))
                Text("Welcome", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Ready to explore? Log in to get started.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))

                Button(
                    enabled = !state.loading,
                    onClick = { launchGoogleSignIn() },
                    modifier = Modifier.height(48.dp).width(260.dp),
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD9E8FF),
                        contentColor = Color.Black
                    )
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_google),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(Modifier.width(10.dp))
                            Text("SIGN IN WITH GOOGLE")
                        }
                    }
                }

                Spacer(Modifier.weight(1f))
                Text("© UTHSmartTasks", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginPreview() = ToDoAppTheme { LoginScreen(onSignedIn = {}) }
