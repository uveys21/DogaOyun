package com.dogakasifleri.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.dogakasifleri.fragments.SettingsFragment
import com.dogakasifleri.utils.PreferenceManager
import com.dogakasifleri.utils.SoundManager

/**
 * SettingsActivity - Kullanıcının uygulama ayarlarını değiştirebileceği aktivite
 * Bu aktivite, ses seviyesi, bildirimler, ebeveyn kontrolü gibi ayarları içerir.
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var preferenceManager: PreferenceManager
    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        // Toolbar ayarları
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ayarlar"
        
        // Ayarlar yöneticisini başlat
        preferenceManager = PreferenceManager(this)
        soundManager = SoundManager(this)
        
        // Settings fragment'ı ekle
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
        
        // Ebeveyn kontrolü butonunu ayarla
        setupParentalControlButton()
    }
    
    /**
     * Ebeveyn kontrolü butonunu ayarlar
     */
    private fun setupParentalControlButton() {
        findViewById<Button>(R.id.buttonParentalControl).setOnClickListener {
            // Ebeveyn kontrolü aktivitesini başlat
            val intent = Intent(this, ParentalControlActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
    
    /**
     * Ayarlar fragment'ı
     */
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            
            // Ses ayarları değişikliğini dinle
            val soundPreference = findPreference<SeekBarPreference>("sound_volume")
            soundPreference?.setOnPreferenceChangeListener { _, newValue ->
                // Ses seviyesini güncelle
                val soundManager = SoundManager(requireContext())
                soundManager.setVolume((newValue as Int).toFloat() / 100f)
                true
            }
            
            // Bildirim ayarları değişikliğini dinle
            val notificationPreference = findPreference<SwitchPreferenceCompat>("notifications_enabled")
            notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
                // Bildirim ayarlarını güncelle
                val preferenceManager = PreferenceManager(requireContext())
                preferenceManager.setNotificationsEnabled(newValue as Boolean)
                true
            }
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        // Geri dönüş animasyonu
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
