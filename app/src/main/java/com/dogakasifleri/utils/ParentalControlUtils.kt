package com.dogakasifleri.utils

import android.content.Context
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * ParentalControlUtils - Ebeveyn kontrolü işlemlerini yöneten yardımcı sınıf
 * Bu sınıf, ebeveyn kontrolü ayarlarını ve şifre korumasını yönetir.
 */
class ParentalControlUtils(private val context: Context) {

    private val preferenceManager = PreferenceManager(context)
    private val encryptionKey = "DogaKasifleriKey" // Gerçek uygulamada daha güvenli bir yöntem kullanılmalı

    /**
     * Ebeveyn kontrolünün etkin olup olmadığını kontrol eder
     */
    fun isParentalControlEnabled(): Boolean {
        return preferenceManager.isParentalControlEnabled()
    }

    /**
     * Ebeveyn kontrolünü etkinleştirir veya devre dışı bırakır
     */
    fun setParentalControlEnabled(enabled: Boolean) {
        preferenceManager.setParentalControlEnabled(enabled)
    }

    /**
     * Şifre korumasının etkin olup olmadığını kontrol eder
     */
    fun isPasswordProtected(): Boolean {
        return preferenceManager.getParentalControlPassword().isNotEmpty()
    }

    /**
     * Şifreyi ayarlar
     */
    fun setPassword(password: String) {
        val encryptedPassword = encryptPassword(password)
        preferenceManager.setParentalControlPassword(encryptedPassword)
    }

    /**
     * Şifreyi kaldırır
     */
    fun removePassword() {
        preferenceManager.setParentalControlPassword("")
    }

    /**
     * Şifreyi doğrular
     */
    fun verifyPassword(password: String): Boolean {
        val storedPassword = preferenceManager.getParentalControlPassword()
        if (storedPassword.isEmpty()) return true
        
        val encryptedPassword = encryptPassword(password)
        return encryptedPassword == storedPassword
    }

    /**
     * Şifreyi şifreler
     */
    private fun encryptPassword(password: String): String {
        try {
            val md = MessageDigest.getInstance("SHA-256")
            val key = md.digest(encryptionKey.toByteArray(Charsets.UTF_8))
            val secretKey = SecretKeySpec(key, "AES")
            
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            
            val encryptedBytes = cipher.doFinal(password.toByteArray(Charsets.UTF_8))
            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * Zaman sınırlamasının etkin olup olmadığını kontrol eder
     */
    fun isTimeRestrictionEnabled(): Boolean {
        return isParentalControlEnabled() && preferenceManager.getDailyUsageLimit() > 0
    }

    /**
     * Günlük kullanım süresini ayarlar (dakika cinsinden)
     */
    fun setDailyUsageLimit(minutes: Int) {
        preferenceManager.setDailyUsageLimit(minutes)
    }

    /**
     * Günlük kullanım süresini döndürür (dakika cinsinden)
     */
    fun getDailyUsageLimit(): Int {
        return preferenceManager.getDailyUsageLimit()
    }

    /**
     * Günlük kullanım süresinin aşılıp aşılmadığını kontrol eder
     */
    fun isUsageLimitExceeded(usageTimeInMinutes: Int): Boolean {
        if (!isTimeRestrictionEnabled()) return false
        return usageTimeInMinutes >= getDailyUsageLimit()
    }

    /**
     * İçerik filtrelemenin etkin olup olmadığını kontrol eder
     */
    fun isContentFilteringEnabled(): Boolean {
        return isParentalControlEnabled() && preferenceManager.isContentFilteringEnabled()
    }

    /**
     * İçerik filtrelemeyi etkinleştirir veya devre dışı bırakır
     */
    fun setContentFilteringEnabled(enabled: Boolean) {
        preferenceManager.setContentFilteringEnabled(enabled)
    }

    /**
     * Zorluk seviyesini ayarlar
     */
    fun setDifficultyLevel(level: String) {
        preferenceManager.setDifficultyLevel(level)
    }

    /**
     * Zorluk seviyesini döndürür
     */
    fun getDifficultyLevel(): String {
        return preferenceManager.getDifficultyLevel()
    }

    /**
     * Kullanıcının yaşına göre uygun içeriği filtreler
     */
    fun filterContentByAge(userAge: Int, contentMinAge: Int): Boolean {
        if (!isContentFilteringEnabled()) return true
        return userAge >= contentMinAge
    }

    /**
     * Ebeveyn kontrolü ayarlarını sıfırlar
     */
    fun resetParentalControls() {
        preferenceManager.setParentalControlEnabled(false)
        preferenceManager.setParentalControlPassword("")
        preferenceManager.setDailyUsageLimit(60) // Varsayılan 60 dakika
        preferenceManager.setContentFilteringEnabled(false)
        preferenceManager.setDifficultyLevel("Orta")
    }
}
