package info.bati11.whenit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import info.bati11.whenit.R
import kotlinx.android.synthetic.main.activity_licenses.*

class LicensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_licenses)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        webView.loadUrl("file:///android_asset/licenses.html")
    }
}
