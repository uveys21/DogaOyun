package com.dogakasifleri.game.engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.dogakasifleri.utils.AnimationUtils

/**
 * AnimationEngine - Oyun animasyonlarını yöneten sınıf
 * Bu sınıf, oyun içi animasyonları yükler, oynatır ve yönetir.
 */
class AnimationEngine(private val context: Context) {
    // Sprite animasyonları
    private val spriteAnimations = mutableMapOf<String, SpriteAnimation>()
    
    // Lottie animasyonları
    private val lottieAnimations = mutableMapOf<String, LottieComposition>()
    
    // Aktif animasyonlar
    private val activeAnimations = mutableListOf<Animation>()
    
    /**
     * Sprite animasyonu yükler
     */
    fun loadSpriteAnimation(name: String, drawableIds: List<Int>, frameDuration: Long): SpriteAnimation {
        val frames = drawableIds.map { ContextCompat.getDrawable(context, it) }
        val animation = SpriteAnimation(name, frames, frameDuration)
        spriteAnimations[name] = animation
        return animation
    }
    
    /**
     * Lottie animasyonu yükler
     */
    fun loadLottieAnimation(name: String, assetName: String) {
        val task = LottieCompositionFactory.fromAsset(context, assetName)
        task.addListener { composition ->
            lottieAnimations[name] = composition
        }
    }
    
    /**
     * Sprite animasyonu başlatır
     */
    fun playSpriteAnimation(name: String, x: Float, y: Float, loop: Boolean = false): SpriteAnimationInstance? {
        val animation = spriteAnimations[name] ?: return null
        val instance = SpriteAnimationInstance(animation, x, y, loop)
        activeAnimations.add(instance)
        return instance
    }
    
    /**
     * Lottie animasyonu başlatır
     */
    fun playLottieAnimation(view: LottieAnimationView, name: String, loop: Boolean = false) {
        val composition = lottieAnimations[name] ?: return
        view.setComposition(composition)
        view.repeatCount = if (loop) -1 else 0
        view.playAnimation()
    }
    
    /**
     * Animasyonu durdurur
     */
    fun stopAnimation(animation: Animation) {
        activeAnimations.remove(animation)
    }
    
    /**
     * Tüm animasyonları günceller
     */
    fun update() {
        val iterator = activeAnimations.iterator()
        while (iterator.hasNext()) {
            val animation = iterator.next()
            animation.update()
            
            // Tamamlanan animasyonları kaldır
            if (animation.isCompleted() && !animation.isLooping()) {
                iterator.remove()
            }
        }
    }
    
    /**
     * Tüm animasyonları çizer
     */
    fun render(canvas: Canvas) {
        for (animation in activeAnimations) {
            animation.render(canvas)
        }
    }
    
    /**
     * Animasyon arayüzü
     */
    interface Animation {
        fun update()
        fun render(canvas: Canvas)
        fun isCompleted(): Boolean
        fun isLooping(): Boolean
    }
    
    /**
     * Sprite animasyonu sınıfı
     */
    class SpriteAnimation(
        val name: String,
        val frames: List<Drawable?>,
        val frameDuration: Long
    )
    
    /**
     * Sprite animasyonu örneği
     */
    inner class SpriteAnimationInstance(
        private val animation: SpriteAnimation,
        private var x: Float,
        private var y: Float,
        private val loop: Boolean
    ) : Animation {
        private var currentFrameIndex = 0
        private var frameTimer = 0L
        private var completed = false
        
        override fun update() {
            if (completed && !loop) return
            
            frameTimer += 16 // ~60 FPS
            
            if (frameTimer >= animation.frameDuration) {
                frameTimer = 0
                currentFrameIndex++
                
                if (currentFrameIndex >= animation.frames.size) {
                    if (loop) {
                        currentFrameIndex = 0
                    } else {
                        currentFrameIndex = animation.frames.size - 1
                        completed = true
                    }
                }
            }
        }
        
        override fun render(canvas: Canvas) {
            val drawable = animation.frames[currentFrameIndex] ?: return
            
            drawable.setBounds(
                x.toInt(),
                y.toInt(),
                (x + drawable.intrinsicWidth).toInt(),
                (y + drawable.intrinsicHeight).toInt()
            )
            
            drawable.draw(canvas)
        }
        
        override fun isCompleted(): Boolean = completed
        
        override fun isLooping(): Boolean = loop
        
        fun setPosition(x: Float, y: Float) {
            this.x = x
            this.y = y
        }
        
        fun reset() {
            currentFrameIndex = 0
            frameTimer = 0
            completed = false
        }
    }
    
    /**
     * Karakter animasyonunu oynatır
     */
    fun playCharacterAnimation(view: LottieAnimationView, animationType: String) {
        AnimationUtils.playCharacterAnimation(view, animationType)
    }
    
    /**
     * Ekosistem animasyonunu oynatır
     */
    fun playEcosystemAnimation(view: LottieAnimationView, ecosystemType: String) {
        AnimationUtils.playEcosystemAnimation(view, ecosystemType)
    }
    
    /**
     * Tüm animasyonları temizler
     */
    fun reset() {
        activeAnimations.clear()
    }
}
