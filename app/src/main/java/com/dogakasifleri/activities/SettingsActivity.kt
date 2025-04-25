package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import androidx.preference.SwitchPreferenceCompat
import com.dogakasifleri.R
import com.dogakasifleri.utils.PreferenceManager
import com.dogakasifleri.utils.SoundManager

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Toolbar ayarları
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Ayarlar"
        }

        // Yöneticileri başlat
        preferenceManager = PreferenceManager(this)
        soundManager = SoundManager(this)

        // Fragment'ı ekle
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }

        // Ebeveyn kontrol butonu
        findViewById<Button>(R.id.buttonParentalControl).setOnClickListener {
            startActivity(Intent(this, ParentalControlActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        // Ses ayarı
        (findPreference<SeekBarPreference>("sound_volume"))?.setOnPreferenceChangeListener { _, newValue ->
            SoundManager(requireContext()).setVolume((newValue as Int).toFloat() / 100f)
            true
        }

        // Bildirim ayarı
        (findPreference<SwitchPreferenceCompat>("notifications_enabled"))?.setOnPreferenceChangeListener { _, newValue ->
            PreferenceManager(requireContext()).setNotificationsEnabled(newValue as Boolean)
            true
        }
    }
}