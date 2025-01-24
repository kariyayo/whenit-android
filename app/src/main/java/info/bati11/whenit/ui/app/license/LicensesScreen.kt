package info.bati11.whenit.ui.app.license

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import info.bati11.whenit.ui.theme.WhenitTheme

@Composable
fun LicensesRoute(
    navController: NavController,
) {
    LicensesScreen(
        onBackClick = {
            navController.popBackStack()
        },
    )
}

@Composable
fun LicensesScreen(
    onBackClick: () -> Unit,
    url: String = "file:///android_asset/licenses.html",
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Licenses")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = Modifier.padding(padding),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewLicensesScreen() {
    WhenitTheme {
        Surface {
            LicensesScreen(
                onBackClick = {}
            )
        }
    }
}