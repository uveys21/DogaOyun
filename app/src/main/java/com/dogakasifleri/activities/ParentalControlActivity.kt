package com.dogakasifleri.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.R

/**
 * Ebeveyn Kontrol ekranı - Ebeveyn ayarları ve kontrolleri
 */
class ParentalControlActivity : AppCompatActivity() {

    private lateinit var etParentPin: EditText
    private lateinit var switchTimeLimit: Switch
    private lateinit var etDailyTimeLimit: EditText
    private lateinit var switchContentFilter: Switch
    private lateinit var switchAllowQuiz: Switch
    private lateinit var btnSaveSettings: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parental_control)

        // UI elemanlarını tanımla
        etParentPin = findViewById(R.id.etParentPin)
        switchTimeLimit = findViewById(R.id.switchTimeLimit)
        etDailyTimeLimit = findViewById(R.id.etDailyTimeLimit)
        switchContentFilter = findViewById(R.id.switchContentFilter)
        switchAllowQuiz = findViewById(R.id.switchAllowQuiz)
        btnSaveSettings = findViewById(R.id.btnSaveSettings)
        btnBack = findViewById(R.id.btnBack)

        // Mevcut ayarları yükle
        loadSettings()

        // Zaman sınırı switch'i değişim olayı
        switchTimeLimit.setOnCheckedChangeListener { _, isChecked ->
            etDailyTimeLimit.isEnabled = isChecked
        }

        // Ayarları kaydet butonu tıklama olayı
        btnSaveSettings.setOnClickListener {
            saveSettings()
        }

        // Geri butonu tıklama olayı
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun loadSettings() {
        // SharedPreferences'dan ayarları yükle
        val sharedPreferences = getSharedPreferences("ParentalControls", MODE_PRIVATE)
        
        val pin = sharedPreferences.getString("PIN", "0000")
        val timeLimit = sharedPreferences.getBoolean("TIME_LIMIT", false)
        val dailyTimeLimit = sharedPreferences.getInt("DAILY_TIME_LIMIT", 60)
        val contentFilter = sharedPreferences.getBoolean("CONTENT_FILTER", true)
        val allowQuiz = sharedPreferences.getBoolean("ALLOW_QUIZ", true)
        
        // UI'ı güncelle
        etParentPin.setText(pin)
        switchTimeLimit.isChecked = timeLimit
        etDailyTimeLimit.setText(dailyTimeLimit.toString())
        etDailyTimeLimit.isEnabled = timeLimit
        switchContentFilter.isChecked = contentFilter
        switchAllowQuiz.isChecked = allowQuiz
    }

    private fun saveSettings() {
        // Girilen PIN'i kontrol et
        val pin = etParentPin.text.toString()
        if (pin.length != 4 || !pin.all { it.isDigit() }) {
            etParentPin.error = "PIN 4 haneli bir sayı olmalıdır"
            return
        }

        // Zaman sınırını kontrol et
        val timeLimit = switchTimeLimit.isChecked
        var dailyTimeLimit = 60
        
        if (timeLimit) {
            try {
                dailyTimeLimit = etDailyTimeLimit.text.toString().toInt()
                if (dailyTimeLimit <= 0) {
                    etDailyTimeLimit.error = "Geçerli bir süre girin"
                    return
                }
            } catch (e: NumberFormatException) {
                etDailyTimeLimit.error = "Geçerli bir süre girin"
                return
            }
        }

        // Ayarları SharedPreferences'a kaydet
        val sharedPreferences = getSharedPreferences("ParentalControls", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        
        editor.putString("PIN", pin)
        editor.putBoolean("TIME_LIMIT", timeLimit)
        editor.putInt("DAILY_TIME_LIMIT", dailyTimeLimit)
        editor.putBoolean("CONTENT_FILTER", switchContentFilter.isChecked)
        editor.putBoolean("ALLOW_QUIZ", switchAllowQuiz.isChecked)
        
        editor.apply()

        // Aktiviteyi kapat
        finish()
    }
}
