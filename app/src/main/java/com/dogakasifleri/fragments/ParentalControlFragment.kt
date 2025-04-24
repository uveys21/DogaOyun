package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dogakasifleri.utils.ParentalControlUtils

/**
 * ParentalControlFragment - Ebeveyn kontrolü ayarlarını içeren fragment
 * Bu fragment, ebeveynlerin uygulama kullanımını kontrol etmelerini sağlayan
 * çeşitli ayarları içerir (kullanım süresi sınırlaması, içerik filtreleme, vb.).
 */
class ParentalControlFragment : Fragment() {

    private lateinit var parentalControlUtils: ParentalControlUtils
    private var isPasswordProtected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_parental_control, container, false)
        
        // Ebeveyn kontrolü yardımcısını başlat
        parentalControlUtils = ParentalControlUtils(requireContext())
        
        // Şifre koruması durumunu kontrol et
        isPasswordProtected = parentalControlUtils.isPasswordProtected()
        
        // UI bileşenlerini ayarla
        setupUI(view)
        
        return view
    }
    
    /**
     * UI bileşenlerini ayarlar
     */
    private fun setupUI(view: View) {
        // Şifre koruma switch'ini ayarla
        val switchPasswordProtection = view.findViewById<SwitchCompat>(R.id.switchPasswordProtection)
        switchPasswordProtection.isChecked = isPasswordProtected
        switchPasswordProtection.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showPasswordSetupDialog()
            } else {
                showPasswordVerificationDialog()
            }
        }
        
        // Kullanım süresi sınırlaması ayarlarını yükle
        setupTimeRestrictionSettings(view)
        
        // İçerik filtreleme ayarlarını yükle
        setupContentFilteringSettings(view)
        
        // Kaydet butonunu ayarla
        view.findViewById<Button>(R.id.buttonSaveParentalSettings).setOnClickListener {
            saveSettings()
        }
    }
    
    /**
     * Şifre ayarlama diyaloğunu gösterir
     */
    private fun showPasswordSetupDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_password_setup, null)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = dialogView.findViewById<EditText>(R.id.editTextConfirmPassword)
        
        AlertDialog.Builder(requireContext())
            .setTitle("Şifre Oluştur")
            .setView(dialogView)
            .setPositiveButton("Kaydet") { _, _ ->
                val password = editTextPassword.text.toString()
                val confirmPassword = editTextConfirmPassword.text.toString()
                
                if (password == confirmPassword && password.isNotEmpty()) {
                    // Şifreyi kaydet
                    parentalControlUtils.setPassword(password)
                    isPasswordProtected = true
                } else {
                    // Hata mesajı göster
                    Toast.makeText(context, "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show()
                    view?.findViewById<SwitchCompat>(R.id.switchPasswordProtection)?.isChecked = false
                }
            }
            .setNegativeButton("İptal") { _, _ ->
                // Switch'i eski haline getir
                view?.findViewById<SwitchCompat>(R.id.switchPasswordProtection)?.isChecked = false
            }
            .show()
    }
    
    /**
     * Şifre doğrulama diyaloğunu gösterir
     */
    private fun showPasswordVerificationDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_password_verification, null)
        val editTextPassword = dialogView.findViewById<EditText>(R.id.editTextPassword)
        
        AlertDialog.Builder(requireContext())
            .setTitle("Şifre Doğrulama")
            .setView(dialogView)
            .setPositiveButton("Doğrula") { _, _ ->
                val password = editTextPassword.text.toString()
                
                if (parentalControlUtils.verifyPassword(password)) {
                    // Şifre korumasını kaldır
                    parentalControlUtils.removePassword()
                    isPasswordProtected = false
                } else {
                    // Hata mesajı göster
                    Toast.makeText(context, "Yanlış şifre!", Toast.LENGTH_SHORT).show()
                    view?.findViewById<SwitchCompat>(R.id.switchPasswordProtection)?.isChecked = true
                }
            }
            .setNegativeButton("İptal") { _, _ ->
                // Switch'i eski haline getir
                view?.findViewById<SwitchCompat>(R.id.switchPasswordProtection)?.isChecked = true
            }
            .show()
    }
    
    /**
     * Kullanım süresi sınırlaması ayarlarını yükler
     */
    private fun setupTimeRestrictionSettings(view: View) {
        // Kullanım süresi sınırlaması switch'ini ayarla
        val switchTimeRestriction = view.findViewById<SwitchCompat>(R.id.switchTimeRestriction)
        switchTimeRestriction.isChecked = parentalControlUtils.isTimeRestrictionEnabled()
        
        // Günlük kullanım süresi seekbar'ını ayarla
        val seekBarDailyUsage = view.findViewById<SeekBar>(R.id.seekBarDailyUsage)
        val textViewDailyUsage = view.findViewById<TextView>(R.id.textViewDailyUsage)
        
        // Mevcut değeri yükle
        val currentDailyUsage = parentalControlUtils.getDailyUsageLimit()
        seekBarDailyUsage.progress = currentDailyUsage / 15 // 15 dakika aralıklarla
        textViewDailyUsage.text = "${currentDailyUsage} dakika"
        
        // Değişikliği dinle
        seekBarDailyUsage.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val minutes = progress * 15 // 15 dakika aralıklarla
                textViewDailyUsage.text = "$minutes dakika"
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    /**
     * İçerik filtreleme ayarlarını yükler
     */
    private fun setupContentFilteringSettings(view: View) {
        // İçerik filtreleme switch'ini ayarla
        val switchContentFiltering = view.findViewById<SwitchCompat>(R.id.switchContentFiltering)
        switchContentFiltering.isChecked = parentalControlUtils.isContentFilteringEnabled()
        
        // Zorluk seviyesi radio butonlarını ayarla
        val radioGroupDifficulty = view.findViewById<RadioGroup>(R.id.radioGroupDifficulty)
        val currentDifficulty = parentalControlUtils.getDifficultyLevel()
        
        when (currentDifficulty) {
            "Kolay" -> radioGroupDifficulty.check(R.id.radioButtonEasy)
            "Orta" -> radioGroupDifficulty.check(R.id.radioButtonMedium)
            "Zor" -> radioGroupDifficulty.check(R.id.radioButtonHard)
        }
    }
    
    /**
     * Ayarları kaydeder
     */
    private fun saveSettings() {
        // Kullanım süresi sınırlaması ayarlarını kaydet
        val switchTimeRestriction = view?.findViewById<SwitchCompat>(R.id.switchTimeRestriction)
        parentalControlUtils.setTimeRestrictionEnabled(switchTimeRestriction?.isChecked ?: false)
        
        // Günlük kullanım süresini kaydet
        val seekBarDailyUsage = view?.findViewById<SeekBar>(R.id.seekBarDailyUsage)
        val dailyUsage = (seekBarDailyUsage?.progress ?: 0) * 15 // 15 dakika aralıklarla
        parentalControlUtils.setDailyUsageLimit(dailyUsage)
        
        // İçerik filtreleme ayarlarını kaydet
        val switchContentFiltering = view?.findViewById<SwitchCompat>(R.id.switchContentFiltering)
        parentalControlUtils.setContentFilteringEnabled(switchContentFiltering?.isChecked ?: false)
        
        // Zorluk seviyesini kaydet
        val radioGroupDifficulty = view?.findViewById<RadioGroup>(R.id.radioGroupDifficulty)
        val difficulty = when (radioGroupDifficulty?.checkedRadioButtonId) {
            R.id.radioButtonEasy -> "Kolay"
            R.id.radioButtonMedium -> "Orta"
            R.id.radioButtonHard -> "Zor"
            else -> "Orta"
        }
        parentalControlUtils.setDifficultyLevel(difficulty)
        
        // Başarı mesajı göster
        Toast.makeText(context, "Ayarlar kaydedildi", Toast.LENGTH_SHORT).show()
    }
}
