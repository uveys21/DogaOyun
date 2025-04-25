package com.dogakasifleri.utils

import android.content.Context
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

/**
 * Animasyon yöneticisi - Lottie animasyonlarını yönetmek için yardımcı sınıf
 */
class AnimationManager {
    companion object {
        private const val TAG = "AnimationManager"
        
        /**
         * Verilen animasyon görünümünde bir Lottie animasyonunu yükler ve oynatır
         * 
         * @param context Uygulama bağlamı
         * @param animationView Animasyonun gösterileceği LottieAnimationView
         * @param animationAsset Animasyon dosyasının assets klasöründeki yolu
         * @param loop Animasyonun döngüde oynatılıp oynatılmayacağı
         * @param autoPlay Animasyonun otomatik başlatılıp başlatılmayacağı
         */
        fun loadAnimation(
            context: Context,
            animationView: LottieAnimationView,
            animationAsset: String,
            loop: Boolean = true,
            autoPlay: Boolean = true
        ) {
            try {
                animationView.setAnimation(animationAsset)
                animationView.repeatCount = if (loop) LottieDrawable.INFINITE else 0
                
                if (autoPlay) {
                    animationView.playAnimation()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Animasyon yüklenirken hata oluştu: ${e.message}")
            }
        }
        
        /**
         * Ekosistem tipine göre uygun animasyon dosyasını döndürür
         * 
         * @param ecosystemType Ekosistem tipi
         * @return Animasyon dosyasının assets klasöründeki yolu
         */
        fun getEcosystemAnimation(ecosystemType: String): String {
            return when (ecosystemType.lowercase()) {
                "orman" -> "raw/forest_animation.json"
                "okyanus" -> "raw/ocean_animation.json"
                "çöl" -> "raw/desert_animation.json"
                "kutuplar" -> "raw/arctic_animation.json"
                else -> "raw/forest_animation.json" // Varsayılan olarak orman animasyonu
            }
        }
        
        /**
         * Başarı animasyonunu yükler ve oynatır
         * 
         * @param context Uygulama bağlamı
         * @param animationView Animasyonun gösterileceği LottieAnimationView
         */
        fun playAchievementAnimation(
            context: Context,
            animationView: LottieAnimationView
        ) {
            loadAnimation(
                context,
                animationView,
                "raw/splash_animation.json",
                loop = false,
                autoPlay = true
            )
        }
    }
}
