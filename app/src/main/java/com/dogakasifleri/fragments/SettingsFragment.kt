package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dogakasifleri.R

/**
 * Ayarlar Fragment - Uygulama ayarlarını gösterir ve değiştirmeye izin verir
 */
class SettingsFragment : Fragment() {

    private lateinit var switchSound: Switch
    private lateinit var switchMusic: Switch
    private lateinit var switchNotifications: Switch
    private lateinit var btnParentalControls: Button
    private lateinit var tvVersion: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unu inflate et
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // UI elemanlarını tanımla
        switchSound = view.findViewById(R.id.switchSound)
        switchMusic = view.findViewById(R.id.switchMusic)
        switchNotifications = view.findViewById(R.id.switchNotifications)
        btnParentalControls = view.findViewById(R.id.btnParentalControls)
        tvVersion = view.findViewById(R.id.tvVersion)

        // Mevcut ayarları yükle
        loadSettings()

        // Ses switch'i değişim olayı
        switchSound.setOnCheckedChangeListener { _, isChecked ->
            saveSound(isChecked)
        }

        // Müzik switch'i değişim olayı
        switchMusic.setOnCheckedChangeListener { _, isChecked ->
            saveMusic(isChecked)
        }

        // Bildirimler switch'i değişim olayı
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            saveNotifications(isChecked)
        }

        // Ebeveyn kontrolleri butonu tıklama olayı
        btnParentalControls.setOnClickListener {
            openParentalControls()
        }

        // Versiyon bilgisini göster
        tvVersion.text = "Versiyon: 1.0.0"

        return view
    }

    private fun loadSettings() {
        // SharedPreferences'dan ayarları yükle
        val sharedPreferences = requireActivity().getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE)
        
        val sound = sharedPreferences.getBoolean("SOUND", true)
        val music = sharedPreferences.getBoolean("MUSIC", true)
        val notifications = sharedPreferences.getBoolean("NOTIFICATIONS", true)
        
        // UI'ı güncelle
        switchSound.isChecked = sound
        switchMusic.isChecked = music
        switchNotifications.isChecked = notifications
    }

    private fun saveSound(enabled: Boolean) {
        // Ses ayarını kaydet
        val sharedPreferences = requireActivity().getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("SOUND", enabled)
        editor.apply()
    }

    private fun saveMusic(enabled: Boolean) {
        // Müzik ayarını kaydet
        val sharedPreferences = requireActivity().getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("MUSIC", enabled)
        editor.apply()
    }

    private fun saveNotifications(enabled: Boolean) {
        // Bildirimler ayarını kaydet
        val sharedPreferences = requireActivity().getSharedPreferences("AppSettings", android.content.Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("NOTIFICATIONS", enabled)
        editor.apply()
    }

    private fun openParentalControls() {
        // Ebeveyn kontrolleri ekranını aç
        val intent = android.content.Intent(activity, com.dogakasifleri.activities.ParentalControlActivity::class.java)
        startActivity(intent)
    }
}
