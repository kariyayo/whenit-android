package info.bati11.whenit.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import dagger.hilt.android.AndroidEntryPoint
import info.bati11.whenit.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(
                    Intent(this, SettingsActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle()
                )
                return true
            }
            R.id.action_licenses -> {
                startActivity(
                    Intent(this, LicensesActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle()
                )
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
