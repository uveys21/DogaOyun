package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.dogakasifleri.R
import com.dogakasifleri.fragments.AchievementsFragment
import com.dogakasifleri.fragments.CollectionFragment
import com.dogakasifleri.fragments.MainMenuFragment
import com.dogakasifleri.fragments.SettingsFragment
import com.google.android.material.navigation.NavigationView

/**
 * Ana ekran - Uygulamanın ana menüsü ve navigasyon yapısı
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar'ı ayarla
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Drawer Layout'u ayarla
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Drawer Toggle'ı ayarla
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Başlangıç fragmentını yükle
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainMenuFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Menü öğelerine göre fragment değiştir
        when (item.itemId) {
            R.id.nav_home -> {
                loadFragment(MainMenuFragment())
            }
            R.id.nav_map -> {
                val intent = Intent(this, MapActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_collection -> {
                loadFragment(CollectionFragment())
            }
            R.id.nav_achievements -> {
                loadFragment(AchievementsFragment())
            }
            R.id.nav_settings -> {
                loadFragment(SettingsFragment())
            }
            R.id.nav_parental_controls -> {
                // Ebeveyn kontrolleri için PIN doğrulama ekranı
                val intent = Intent(this, ParentalControlActivity::class.java)
                startActivity(intent)
            }
        }

        // Drawer'ı kapat
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        // Drawer açıksa kapat, değilse normal back işlemi yap
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
