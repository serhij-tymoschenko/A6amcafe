package dev.tymoshenko.a6amcafe.ui.screens.auth.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    value: String,
    placeholderText: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .height(48.dp),
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(32),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholderText,
                fontSize = 12.sp,
                color = Color.Black
            )
        },
        textStyle = TextStyle(fontSize = 14.sp)
    )
}

@Preview
@Composable
private fun AuthTextFieldPreview() {
    AuthTextField(
        value = "Hello",
        onValueChange = {},
        placeholderText = "World"
    )
}