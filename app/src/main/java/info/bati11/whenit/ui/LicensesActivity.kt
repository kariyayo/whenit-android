package info.bati11.whenit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R

@AndroidEntryPoint
class LicensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licenses)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<WebView>(R.id.webView).loadUrl("file:///android_asset/licenses.html")
    }
}
