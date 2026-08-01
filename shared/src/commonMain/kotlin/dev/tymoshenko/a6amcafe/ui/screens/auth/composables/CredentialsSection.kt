package dev.tymoshenko.a6amcafe.ui.screens.auth.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tymoshenko.a6amcafe.ui.theme.orangeAccent

@Composable
fun CredentialSection(
    modifier: Modifier = Modifier,
    onPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    password: String,
    email: String,
    onAuth: () -> Unit,
    onGoogleAuth: () -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TextLogo()

            Spacer(modifier = Modifier.weight(2F))

            SocialCredentials(
                onGoogleAuth = onGoogleAuth,
                onGithubAuth = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                modifier = Modifier.fillMaxWidth(0.8F),
                value = email,
                placeholderText = "Email"
            ) { newEmail ->
                onEmailChange.invoke(newEmail)
            }

            Spacer(modifier = Modifier.height(12.dp))

            AuthTextField(
                modifier = Modifier.fillMaxWidth(0.8F),
                value = password,
                placeholderText = "Password"
            ) { newPassword ->
                onPasswordChange.invoke(newPassword)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8F),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = orangeAccent,
                    contentColor = Color.White
                ),
                onClick = onAuth
            ) {
                Text(
                    text = "Sign in"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(0.8F),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    modifier = Modifier.height(36.dp),
                    onClick = { }
                ) {
                    Text(
                        text = "Don't have an account?",
                        color = Color.Black.copy(alpha = 0.7F),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(3F))
        }
    }
}

@Preview
@Composable
private fun CredentialSectionPreview() {
    CredentialSection(
        onPasswordChange = {},
        onEmailChange = {},
        password = "",
        email = "",
        onAuth = {},
        onGoogleAuth = {}
    )
}