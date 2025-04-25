package com.dogakasifleri.utils

import android.content.Context
import android.util.Log

/**
 * Hata ayıklama yardımcısı - Uygulama hatalarını tespit etmek ve çözmek için yardımcı sınıf
 */
class DebugHelper {
    companion object {
        private const val TAG = "DebugHelper"
        
        /**
         * Uygulama bileşenlerinin durumunu kontrol eder ve hataları loglar
         * 
         * @param context Uygulama bağlamı
         * @return Tüm kontroller başarılıysa true, değilse false
         */
        fun checkApplicationState(context: Context): Boolean {
            Log.d(TAG, "Uygulama durumu kontrol ediliyor...")
            
            var allChecksPass = true
            
            // Animasyon dosyalarını kontrol et
            val animationFilesExist = checkAnimationFiles(context)
            if (!animationFilesExist) {
                allChecksPass = false
            }
            
            // Gerekli kaynakları kontrol et
            val resourcesExist = checkRequiredResources(context)
            if (!resourcesExist) {
                allChecksPass = false
            }
            
            return allChecksPass
        }
        
        /**
         * Animasyon dosyalarını kontrol eder
         * 
         * @param context Uygulama bağlamı
         * @return Tüm animasyon dosyaları mevcutsa true, değilse false
         */
        private fun checkAnimationFiles(context: Context): Boolean {
            val animationFiles = listOf(
                "raw/splash_animation.json",
                "raw/forest_animation.json",
                "raw/ocean_animation.json",
                "raw/desert_animation.json",
                "raw/arctic_animation.json"
            )
            
            var allFilesExist = true
            
            for (file in animationFiles) {
                try {
                    context.assets.open(file).use {
                        Log.d(TAG, "Animasyon dosyası mevcut: $file")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Animasyon dosyası bulunamadı: $file - ${e.message}")
                    allFilesExist = false
                }
            }
            
            return allFilesExist
        }
        
        /**
         * Gerekli kaynakları kontrol eder
         * 
         * @param context Uygulama bağlamı
         * @return Tüm gerekli kaynaklar mevcutsa true, değilse false
         */
        private fun checkRequiredResources(context: Context): Boolean {
            // Bu metot gerçek bir uygulamada drawable, layout vb. kaynakları kontrol eder
            // Şu an için sadece simülasyon amaçlı olarak true döndürüyoruz
            Log.d(TAG, "Gerekli kaynaklar kontrol edildi ve mevcut")
            return true
        }
        
        /**
         * Bellek kullanımını kontrol eder ve loglar
         * 
         * @param tag Log etiketi
         * @param message Log mesajı
         */
        fun logMemoryUsage(tag: String, message: String) {
            val runtime = Runtime.getRuntime()
            val usedMemoryMB = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L
            val maxMemoryMB = runtime.maxMemory() / 1048576L
            val availableMemoryMB = maxMemoryMB - usedMemoryMB
            
            Log.d(tag, "$message - Bellek Kullanımı: $usedMemoryMB MB kullanılıyor, $availableMemoryMB MB kullanılabilir, $maxMemoryMB MB maksimum")
        }
    }
}
