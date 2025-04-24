package com.dogakasifleri.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView

/**
 * AnimationUtils - Uygulama genelinde kullanılan animasyonları yöneten yardımcı sınıf
 * Bu sınıf, UI bileşenlerine uygulanan çeşitli animasyonları içerir.
 */
class AnimationUtils {
    companion object {
        /**
         * RecyclerView öğelerine animasyon uygular
         */
        fun applyItemAnimations(recyclerView: RecyclerView) {
            recyclerView.itemAnimator?.apply {
                addDuration = 300
                removeDuration = 300
                moveDuration = 300
                changeDuration = 300
            }
        }
        
        /**
         * Görünüme fade-in animasyonu uygular
         */
        fun applyFadeInAnimation(view: View, context: Context) {
            val animation = android.view.animation.AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            animation.duration = 500
            view.startAnimation(animation)
        }
        
        /**
         * Görünüme fade-out animasyonu uygular
         */
        fun applyFadeOutAnimation(view: View, context: Context) {
            val animation = android.view.animation.AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
            animation.duration = 500
            view.startAnimation(animation)
        }
        
        /**
         * Görünüme bounce animasyonu uygular
         */
        fun applyBounceAnimation(view: View) {
            val scaleAnimation = ScaleAnimation(
                0.8f, 1.0f, 0.8f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 300
            scaleAnimation.repeatMode = Animation.REVERSE
            scaleAnimation.repeatCount = 1
            view.startAnimation(scaleAnimation)
        }
        
        /**
         * Görünüme pulse animasyonu uygular
         */
        fun applyPulseAnimation(view: View) {
            val scaleAnimation = ScaleAnimation(
                1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 300
            scaleAnimation.repeatMode = Animation.REVERSE
            scaleAnimation.repeatCount = Animation.INFINITE
            view.startAnimation(scaleAnimation)
        }
        
        /**
         * Harita görünümüne animasyon uygular
         */
        fun applyMapAnimation(mapView: View?) {
            if (mapView == null) return
            
            val scaleAnimation = ScaleAnimation(
                0.9f, 1.0f, 0.9f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 500
            mapView.startAnimation(scaleAnimation)
        }
        
        /**
         * Oyun animasyonlarını uygular
         */
        fun applyGameAnimations(gameContainer: View?) {
            if (gameContainer == null) return
            
            val scaleAnimation = ScaleAnimation(
                0.8f, 1.0f, 0.8f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = 500
            gameContainer.startAnimation(scaleAnimation)
        }
    }
}
