package com.dogakasifleri.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * PreferenceManager - Uygulama tercihlerini yöneten yardımcı sınıf
 * Bu sınıf, SharedPreferences kullanarak uygulama ayarlarını saklar ve yönetir.
 */
class PreferenceManager(context: Context) {

    internal val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    internal val editor: SharedPreferences.Editor = preferences.edit()

    companion object {
        private const val PREF_NAME = "dogakasifleri_preferences"
        
        // Kullanıcı tercihleri
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_AGE = "user_age"
        private const val KEY_USER_AVATAR_ID = "user_avatar_id"
        private const val KEY_IS_FIRST_LOGIN = "is_first_login"
        private const val KEY_LAST_LOGIN_TIME = "last_login_time"
        
        // Ses ayarları
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_SOUND_VOLUME = "sound_volume"
        private const val KEY_MUSIC_ENABLED = "music_enabled"
        private const val KEY_MUSIC_VOLUME = "music_volume"
        
        // Bildirim ayarları
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_DAILY_REMINDER_ENABLED = "daily_reminder_enabled"
        private const val KEY_DAILY_REMINDER_TIME = "daily_reminder_time"
        
        // Ebeveyn kontrolü ayarları
        private const val KEY_PARENTAL_CONTROL_ENABLED = "parental_control_enabled"
        private const val KEY_PARENTAL_CONTROL_PASSWORD = "parental_control_password"
        private const val KEY_DAILY_USAGE_LIMIT = "daily_usage_limit"
        private const val KEY_CONTENT_FILTERING_ENABLED = "content_filtering_enabled"
        private const val KEY_DIFFICULTY_LEVEL = "difficulty_level"
        
        // Oyun ayarları
        private const val KEY_TUTORIAL_COMPLETED = "tutorial_completed"
        private const val KEY_SELECTED_LANGUAGE = "selected_language"
        private const val KEY_SELECTED_THEME = "selected_theme"
        private const val KEY_AUTO_SAVE_ENABLED = "auto_save_enabled"
    }

    /**
     * Kullanıcı ID'sini kaydeder
     */
    fun setUserId(userId: String) {
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    /**
     * Kullanıcı ID'sini döndürür
     */
    fun getUserId(): String {
        return preferences.getString(KEY_USER_ID, "") ?: ""
    }

    /**
     * Kullanıcı adını kaydeder
     */
    fun setUserName(userName: String) {
        editor.putString(KEY_USER_NAME, userName)
        editor.apply()
    }

    /**
     * Kullanıcı adını döndürür
     */
    fun getUserName(): String {
        return preferences.getString(KEY_USER_NAME, "") ?: ""
    }

    /**
     * Kullanıcı yaşını kaydeder
     */
    fun setUserAge(age: Int) {
        editor.putInt(KEY_USER_AGE, age)
        editor.apply()
    }

    /**
     * Kullanıcı yaşını döndürür
     */
    fun getUserAge(): Int {
        return preferences.getInt(KEY_USER_AGE, 0)
    }

    /**
     * Kullanıcı avatar ID'sini kaydeder
     */
    fun setUserAvatarId(avatarId: Int) {
        editor.putInt(KEY_USER_AVATAR_ID, avatarId)
        editor.apply()
    }

    /**
     * Kullanıcı avatar ID'sini döndürür
     */
    fun getUserAvatarId(): Int {
        return preferences.getInt(KEY_USER_AVATAR_ID, 1)
    }

    /**
     * İlk giriş durumunu kaydeder
     */
    fun setFirstLogin(isFirstLogin: Boolean) {
        editor.putBoolean(KEY_IS_FIRST_LOGIN, isFirstLogin)
        editor.apply()
    }

    /**
     * İlk giriş durumunu döndürür
     */
    fun isFirstLogin(): Boolean {
        return preferences.getBoolean(KEY_IS_FIRST_LOGIN, true)
    }

    /**
     * Son giriş zamanını kaydeder
     */
    fun setLastLoginTime(time: Long) {
        editor.putLong(KEY_LAST_LOGIN_TIME, time)
        editor.apply()
    }

    /**
     * Son giriş zamanını döndürür
     */
    fun getLastLoginTime(): Long {
        return preferences.getLong(KEY_LAST_LOGIN_TIME, 0)
    }

    /**
     * Ses durumunu kaydeder
     */
    fun setSoundEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_SOUND_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Ses durumunu döndürür
     */
    fun isSoundEnabled(): Boolean {
        return preferences.getBoolean(KEY_SOUND_ENABLED, true)
    }

    /**
     * Ses seviyesini kaydeder
     */
    fun setSoundVolume(volume: Float) {
        editor.putFloat(KEY_SOUND_VOLUME, volume)
        editor.apply()
    }

    /**
     * Ses seviyesini döndürür
     */
    fun getSoundVolume(): Float {
        return preferences.getFloat(KEY_SOUND_VOLUME, 1.0f)
    }

    /**
     * Müzik durumunu kaydeder
     */
    fun setMusicEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_MUSIC_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Müzik durumunu döndürür
     */
    fun isMusicEnabled(): Boolean {
        return preferences.getBoolean(KEY_MUSIC_ENABLED, true)
    }

    /**
     * Müzik seviyesini kaydeder
     */
    fun setMusicVolume(volume: Float) {
        editor.putFloat(KEY_MUSIC_VOLUME, volume)
        editor.apply()
    }

    /**
     * Müzik seviyesini döndürür
     */
    fun getMusicVolume(): Float {
        return preferences.getFloat(KEY_MUSIC_VOLUME, 0.8f)
    }

    /**
     * Bildirim durumunu kaydeder
     */
    fun setNotificationsEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Bildirim durumunu döndürür
     */
    fun isNotificationsEnabled(): Boolean {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
    }

    /**
     * Günlük hatırlatıcı durumunu kaydeder
     */
    fun setDailyReminderEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_DAILY_REMINDER_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Günlük hatırlatıcı durumunu döndürür
     */
    fun isDailyReminderEnabled(): Boolean {
        return preferences.getBoolean(KEY_DAILY_REMINDER_ENABLED, false)
    }

    /**
     * Günlük hatırlatıcı zamanını kaydeder
     */
    fun setDailyReminderTime(timeInMillis: Long) {
        editor.putLong(KEY_DAILY_REMINDER_TIME, timeInMillis)
        editor.apply()
    }

    /**
     * Günlük hatırlatıcı zamanını döndürür
     */
    fun getDailyReminderTime(): Long {
        return preferences.getLong(KEY_DAILY_REMINDER_TIME, 0)
    }

    /**
     * Ebeveyn kontrolü durumunu kaydeder
     */
    fun setParentalControlEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_PARENTAL_CONTROL_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Ebeveyn kontrolü durumunu döndürür
     */
    fun isParentalControlEnabled(): Boolean {
        return preferences.getBoolean(KEY_PARENTAL_CONTROL_ENABLED, false)
    }

    /**
     * Ebeveyn kontrolü şifresini kaydeder
     */
    fun setParentalControlPassword(password: String) {
        editor.putString(KEY_PARENTAL_CONTROL_PASSWORD, password)
        editor.apply()
    }

    /**
     * Ebeveyn kontrolü şifresini döndürür
     */
    fun getParentalControlPassword(): String {
        return preferences.getString(KEY_PARENTAL_CONTROL_PASSWORD, "") ?: ""
    }

    /**
     * Günlük kullanım sınırını kaydeder (dakika cinsinden)
     */
    fun setDailyUsageLimit(minutes: Int) {
        editor.putInt(KEY_DAILY_USAGE_LIMIT, minutes)
        editor.apply()
    }

    /**
     * Günlük kullanım sınırını döndürür (dakika cinsinden)
     */
    fun getDailyUsageLimit(): Int {
        return preferences.getInt(KEY_DAILY_USAGE_LIMIT, 60) // Varsayılan 60 dakika
    }

    /**
     * İçerik filtreleme durumunu kaydeder
     */
    fun setContentFilteringEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_CONTENT_FILTERING_ENABLED, enabled)
        editor.apply()
    }

    /**
     * İçerik filtreleme durumunu döndürür
     */
    fun isContentFilteringEnabled(): Boolean {
        return preferences.getBoolean(KEY_CONTENT_FILTERING_ENABLED, false)
    }

    /**
     * Zorluk seviyesini kaydeder
     */
    fun setDifficultyLevel(level: String) {
        editor.putString(KEY_DIFFICULTY_LEVEL, level)
        editor.apply()
    }

    /**
     * Zorluk seviyesini döndürür
     */
    fun getDifficultyLevel(): String {
        return preferences.getString(KEY_DIFFICULTY_LEVEL, "Orta") ?: "Orta"
    }

    /**
     * Öğretici tamamlanma durumunu kaydeder
     */
    fun setTutorialCompleted(completed: Boolean) {
        editor.putBoolean(KEY_TUTORIAL_COMPLETED, completed)
        editor.apply()
    }

    /**
     * Öğretici tamamlanma durumunu döndürür
     */
    fun isTutorialCompleted(): Boolean {
        return preferences.getBoolean(KEY_TUTORIAL_COMPLETED, false)
    }

    /**
     * Seçilen dili kaydeder
     */
    fun setSelectedLanguage(language: String) {
        editor.putString(KEY_SELECTED_LANGUAGE, language)
        editor.apply()
    }

    /**
     * Seçilen dili döndürür
     */
    fun getSelectedLanguage(): String {
        return preferences.getString(KEY_SELECTED_LANGUAGE, "tr") ?: "tr" // Varsayılan Türkçe
    }

    /**
     * Seçilen temayı kaydeder
     */
    fun setSelectedTheme(theme: String) {
        editor.putString(KEY_SELECTED_THEME, theme)
        editor.apply()
    }

    /**
     * Seçilen temayı döndürür
     */
    fun getSelectedTheme(): String {
        return preferences.getString(KEY_SELECTED_THEME, "light") ?: "light" // Varsayılan açık tema
    }

    /**
     * Otomatik kaydetme durumunu kaydeder
     */
    fun setAutoSaveEnabled(enabled: Boolean) {
        editor.putBoolean(KEY_AUTO_SAVE_ENABLED, enabled)
        editor.apply()
    }

    /**
     * Otomatik kaydetme durumunu döndürür
     */
    fun isAutoSaveEnabled(): Boolean {
        return preferences.getBoolean(KEY_AUTO_SAVE_ENABLED, true)
    }

    /**
     * Tüm tercihleri temizler
     */
    fun clearAllPreferences() {
        editor.clear()
        editor.apply()
    }
}
