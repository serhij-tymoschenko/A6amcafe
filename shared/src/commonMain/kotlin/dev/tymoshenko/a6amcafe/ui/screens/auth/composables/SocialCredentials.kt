package dev.tymoshenko.a6amcafe.ui.screens.auth.composables

import a6amcafe.shared.generated.resources.Res
import a6amcafe.shared.generated.resources.github
import a6amcafe.shared.generated.resources.google
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialCredentials(
    modifier: Modifier = Modifier,
    onGoogleAuth: () -> Unit,
    onGithubAuth: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(40.dp),
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = Color.White,
            ),
            onClick = onGoogleAuth
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(Res.drawable.google),
                tint = null,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            modifier = Modifier
                .size(40.dp),
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = Color.White,
            ),
            onClick = onGithubAuth,
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(Res.drawable.github),
                tint = null,
                contentDescription = null
            )
        }
    }
}