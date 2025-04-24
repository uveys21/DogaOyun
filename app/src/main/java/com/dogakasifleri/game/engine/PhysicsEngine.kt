package com.dogakasifleri.game.engine

import android.graphics.Canvas
import com.dogakasifleri.game.engine.GameEngine.GameObject

/**
 * PhysicsEngine - Oyun fiziğini yöneten sınıf
 * Bu sınıf, oyun nesnelerinin hareket, yerçekimi ve diğer fiziksel özelliklerini yönetir.
 */
class PhysicsEngine {
    // Fizik sabitleri
    private val gravity = 0.5f
    private val friction = 0.8f
    private val bounceCoefficient = 0.7f
    
    // Dünya sınırları
    private var worldWidth = 0f
    private var worldHeight = 0f
    
    // Nesne hızları
    private val velocities = mutableMapOf<GameObject, Pair<Float, Float>>()
    
    /**
     * Dünya boyutlarını ayarlar
     */
    fun setWorldBounds(width: Float, height: Float) {
        worldWidth = width
        worldHeight = height
    }
    
    /**
     * Tüm oyun nesnelerini günceller
     */
    fun update(gameObjects: List<GameObject>) {
        for (gameObject in gameObjects) {
            // Nesnenin hızını al veya oluştur
            val velocity = velocities[gameObject] ?: Pair(0f, 0f)
            
            // Yerçekimini uygula
            val newVelocityY = velocity.second + gravity
            
            // Sürtünmeyi uygula
            val newVelocityX = velocity.first * friction
            
            // Yeni hızı kaydet
            velocities[gameObject] = Pair(newVelocityX, newVelocityY)
            
            // Nesneyi hareket ettir
            moveObject(gameObject, newVelocityX, newVelocityY)
            
            // Dünya sınırlarını kontrol et
            checkWorldBounds(gameObject)
        }
    }
    
    /**
     * Nesneyi hareket ettirir
     */
    private fun moveObject(gameObject: GameObject, velocityX: Float, velocityY: Float) {
        // Bu metot gerçek bir oyun motorunda nesnenin pozisyonunu değiştirirdi
        // Şimdilik sadece konsept olarak bırakıyoruz
    }
    
    /**
     * Dünya sınırlarını kontrol eder
     */
    private fun checkWorldBounds(gameObject: GameObject) {
        val x = gameObject.getX()
        val y = gameObject.getY()
        val width = gameObject.getWidth()
        val height = gameObject.getHeight()
        
        // Sağ ve sol sınırları kontrol et
        if (x < 0) {
            // Sol sınıra çarptı, sıçrat
            val velocity = velocities[gameObject] ?: Pair(0f, 0f)
            velocities[gameObject] = Pair(-velocity.first * bounceCoefficient, velocity.second)
        } else if (x + width > worldWidth) {
            // Sağ sınıra çarptı, sıçrat
            val velocity = velocities[gameObject] ?: Pair(0f, 0f)
            velocities[gameObject] = Pair(-velocity.first * bounceCoefficient, velocity.second)
        }
        
        // Üst ve alt sınırları kontrol et
        if (y < 0) {
            // Üst sınıra çarptı, sıçrat
            val velocity = velocities[gameObject] ?: Pair(0f, 0f)
            velocities[gameObject] = Pair(velocity.first, -velocity.second * bounceCoefficient)
        } else if (y + height > worldHeight) {
            // Alt sınıra çarptı, sıçrat
            val velocity = velocities[gameObject] ?: Pair(0f, 0f)
            velocities[gameObject] = Pair(velocity.first, -velocity.second * bounceCoefficient)
        }
    }
    
    /**
     * Nesneye kuvvet uygular
     */
    fun applyForce(gameObject: GameObject, forceX: Float, forceY: Float) {
        val velocity = velocities[gameObject] ?: Pair(0f, 0f)
        velocities[gameObject] = Pair(velocity.first + forceX, velocity.second + forceY)
    }
    
    /**
     * Nesneye zıplama kuvveti uygular
     */
    fun applyJump(gameObject: GameObject) {
        val velocity = velocities[gameObject] ?: Pair(0f, 0f)
        velocities[gameObject] = Pair(velocity.first, -10f) // Yukarı doğru kuvvet
    }
    
    /**
     * Nesnenin hızını ayarlar
     */
    fun setVelocity(gameObject: GameObject, velocityX: Float, velocityY: Float) {
        velocities[gameObject] = Pair(velocityX, velocityY)
    }
    
    /**
     * Nesnenin hızını döndürür
     */
    fun getVelocity(gameObject: GameObject): Pair<Float, Float> {
        return velocities[gameObject] ?: Pair(0f, 0f)
    }
    
    /**
     * Nesnenin yere değip değmediğini kontrol eder
     */
    fun isOnGround(gameObject: GameObject): Boolean {
        return gameObject.getY() + gameObject.getHeight() >= worldHeight
    }
    
    /**
     * Tüm fizik verilerini temizler
     */
    fun reset() {
        velocities.clear()
    }
}
