package dev.tymoshenko.a6amcafe.ui.screens.auth.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = "6am café",
            fontStyle = FontStyle.Italic,
            color = Color.White,
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.fillMaxWidth())
    }
}