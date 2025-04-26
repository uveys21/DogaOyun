package com.dogakasifleri.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool

/**
 * SoundManager - Uygulama ses ve müziklerini yöneten yardımcı sınıf
 * Bu sınıf, oyun içi ses efektlerini ve müzikleri çalar ve yönetir.
 */
class SoundManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null
    private val soundMap = HashMap<String, Int>()
    private var volume: Float = 1.0f
    private var isMuted: Boolean = false //isMuted değişkeni var yapıldı.
    private val preferenceManager = PreferenceManager(context)

    init {
        // SoundPool'u başlat
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()
        
        // Tercihlerden ses ayarlarını yükle
        volume = preferenceManager.getSoundVolume()
        isMuted = !preferenceManager.isSoundEnabled()
        
        // Ses efektlerini yükle
        loadSounds()
    }

    /**
     * Ses efektlerini yükler
     */
    private fun loadSounds() {
        try {
            // Ses efektlerini yükle
            soundMap["click"] = soundPool?.load(context.assets.openFd("sounds/click.wav"), 1) ?: 0
            soundMap["success"] = soundPool?.load(context.assets.openFd("sounds/success.wav"), 1) ?: 0
            soundMap["failure"] = soundPool?.load(context.assets.openFd("sounds/failure.wav"), 1) ?: 0
            soundMap["achievement"] = soundPool?.load(context.assets.openFd("sounds/achievement.wav"), 1) ?: 0
            soundMap["collect"] = soundPool?.load(context.assets.openFd("sounds/collect.wav"), 1) ?: 0
            soundMap["level_up"] = soundPool?.load(context.assets.openFd("sounds/level_up.wav"), 1) ?: 0
            soundMap["quest_complete"] = soundPool?.load(context.assets.openFd("sounds/quest_complete.wav"), 1) ?: 0
            
            // Hayvan sesleri
            soundMap["jaguar"] = soundPool?.load(context.assets.openFd("sounds/animals/jaguar.wav"), 1) ?: 0
            soundMap["anaconda"] = soundPool?.load(context.assets.openFd("sounds/animals/anaconda.wav"), 1) ?: 0
            soundMap["tuna"] = soundPool?.load(context.assets.openFd("sounds/animals/tuna.wav"), 1) ?: 0
            soundMap["camel"] = soundPool?.load(context.assets.openFd("sounds/animals/camel.wav"), 1) ?: 0
            soundMap["polar_bear"] = soundPool?.load(context.assets.openFd("sounds/animals/polar_bear.wav"), 1) ?: 0
            soundMap["penguin"] = soundPool?.load(context.assets.openFd("sounds/animals/penguin.wav"), 1) ?: 0
            
            // Ekosistem sesleri
            soundMap["forest"] = soundPool?.load(context.assets.openFd("sounds/ecosystems/forest.wav"), 1) ?: 0
            soundMap["ocean"] = soundPool?.load(context.assets.openFd("sounds/ecosystems/ocean.wav"), 1) ?: 0
            soundMap["desert"] = soundPool?.load(context.assets.openFd("sounds/ecosystems/desert.wav"), 1) ?: 0
            soundMap["arctic"] = soundPool?.load(context.assets.openFd("sounds/ecosystems/arctic.wav"), 1) ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Ses efekti çalar
     */
    fun playSound(soundName: String) {
        if (isMuted) return
        
        val soundId = soundMap[soundName] ?: return
        soundPool?.play(soundId, volume, volume, 1, 0, 1.0f)
    }

    /**
     * Hayvan sesi çalar
     */
    fun playAnimalSound(animalName: String) {
        playSound(animalName.toLowerCase())
    }

    /**
     * Ekosistem arka plan sesini çalar
     */
    fun playEcosystemSound(ecosystemType: String) {
        playSound(ecosystemType.toLowerCase())
    }

    /**
     * Arka plan müziğini çalar
     */
    fun playMusic(musicName: String, isLooping: Boolean = true) {
        if (!preferenceManager.isMusicEnabled()) return
        
        try {
            // Önceki müziği durdur
            stopMusic()
            
            // Yeni müziği başlat
            val assetFileDescriptor = context.assets.openFd("music/$musicName.mp3")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.length)
                setVolume(preferenceManager.getMusicVolume(), preferenceManager.getMusicVolume())
                isLooping = isLooping
                prepare()
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Arka plan müziğini durdurur
     */
    fun stopMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mediaPlayer = null
    }

    /**
     * Arka plan müziğini duraklatır
     */
    fun pauseMusic() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
            }
        }
    }

    /**
     * Arka plan müziğini devam ettirir
     */
    fun resumeMusic() {
        mediaPlayer?.apply {
            if (!isPlaying) {
                start()
            }
        }
    }

    /**
     * Ses seviyesini ayarlar
     */
    fun setVolume(volume: Float) {
        this.volume = volume
        preferenceManager.setSoundVolume(volume)
        
        // MediaPlayer ses seviyesini güncelle
        mediaPlayer?.setVolume(preferenceManager.getMusicVolume(), preferenceManager.getMusicVolume())
    }

    /**
     * Sesi açar/kapatır
     */
    fun setMuted(muted: Boolean) {
        isMuted = muted
        preferenceManager.setSoundEnabled(!muted)
        
        if (muted) {
            pauseMusic()
        } else {
            resumeMusic()
        }
    }

    /**
     * Kaynakları temizler
     */
    fun release() {
        stopMusic()
        soundPool?.release()
        soundPool = null
    }
}
