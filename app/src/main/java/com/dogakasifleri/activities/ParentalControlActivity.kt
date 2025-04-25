package com.dogakasifleri.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.R

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

        // Toolbar setup
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize UI components
        etParentPin = findViewById(R.id.etParentPin)
        switchTimeLimit = findViewById(R.id.switchTimeLimit)
        etDailyTimeLimit = findViewById(R.id.etDailyTimeLimit)
        switchContentFilter = findViewById(R.id.switchContentFilter)
        switchAllowQuiz = findViewById(R.id.switchAllowQuiz)
        btnSaveSettings = findViewById(R.id.btnSaveSettings)
        btnBack = findViewById(R.id.btnBack)

        loadSettings()

        btnBack.setOnClickListener { finish() }

        switchTimeLimit.setOnCheckedChangeListener { _, isChecked ->
            etDailyTimeLimit.isEnabled = isChecked
        }

        btnSaveSettings.setOnClickListener {
            saveSettings()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun loadSettings() {
        val sharedPreferences = getSharedPreferences("ParentalControls", Context.MODE_PRIVATE)
        val pin = sharedPreferences.getString("PIN", "0000")
        val timeLimit = sharedPreferences.getBoolean("TIME_LIMIT", false)
        val dailyTimeLimit = sharedPreferences.getInt("DAILY_TIME_LIMIT", 60)
        val contentFilter = sharedPreferences.getBoolean("CONTENT_FILTER", true)
        val allowQuiz = sharedPreferences.getBoolean("ALLOW_QUIZ", true)

        etParentPin.setText(pin)
        switchTimeLimit.isChecked = timeLimit
        etDailyTimeLimit.setText(dailyTimeLimit.toString())
        etDailyTimeLimit.isEnabled = timeLimit
        switchContentFilter.isChecked = contentFilter
        switchAllowQuiz.isChecked = allowQuiz
    }

    private fun saveSettings() {
        val pin = etParentPin.text.toString()
        if (pin.length != 4 || !pin.all { it.isDigit() }) {
            etParentPin.error = "PIN 4 haneli bir sayı olmalıdır"
            return
        }

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

        val sharedPreferences = getSharedPreferences("ParentalControls", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString("PIN", pin)
            putBoolean("TIME_LIMIT", timeLimit)
            putInt("DAILY_TIME_LIMIT", dailyTimeLimit)
            putBoolean("CONTENT_FILTER", switchContentFilter.isChecked)
            putBoolean("ALLOW_QUIZ", switchAllowQuiz.isChecked)
            apply()
        }

        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}