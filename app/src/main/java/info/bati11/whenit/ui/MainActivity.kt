package info.bati11.whenit.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.ui.navigation.AppRouter
import info.bati11.whenit.ui.theme.WhenitTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhenitTheme {
                AppRouter()
            }
        }
    }
}
