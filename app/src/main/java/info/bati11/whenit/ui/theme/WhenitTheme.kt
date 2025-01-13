package info.bati11.whenit.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun WhenitTheme(
    content: @Composable () -> Unit
) {
    val colorScheme =
    MaterialTheme(
        colors = WhenitColorScheme,
        content = content,
    )
}

private val WhenitColorScheme = lightColors(
    primary = Color(0xff8badbb),
    primaryVariant = Color(0xff5d7e8b),
    secondary = Color(0xfff4cf99),
    onBackground = Color(0xff5d7e8b),
)
