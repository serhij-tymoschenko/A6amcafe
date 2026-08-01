package dev.tymoshenko.a6amcafe.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.tymoshenko.a6amcafe.ui.screens.auth.composables.CredentialSection
import dev.tymoshenko.a6amcafe.ui.screens.auth.composables.LogoSection
import dev.tymoshenko.a6amcafe.ui.theme.creamBackground
import dev.tymoshenko.a6amcafe.ui.theme.greyBackground

@Composable
fun Auth() {
    //val viewModel = koinViewModel<AuthViewModel>()


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val onEmailChange = { newEmail: String ->
        email = newEmail
    }

    val onPasswordChange = { newPassword: String ->
        password = newPassword
    }

    val onAuth = { }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        LogoSection(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxSize(0.3F)
                .background(greyBackground)
        )

        CredentialSection(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.7F)
                .background(creamBackground),
            email = email,
            password = password,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onAuth = onAuth,
            onGoogleAuth = { }
        )
    }
}


@Preview
@Composable
private fun AuthPreview() {
    Auth()
}