package com.dogakasifleri.game.engine

import com.dogakasifleri.game.engine.GameEngine.GameObject

/**
 * CollisionDetector - Çarpışma tespitini yöneten sınıf
 * Bu sınıf, oyun nesneleri arasındaki çarpışmaları tespit eder ve gerekli olayları tetikler.
 */
class CollisionDetector {
    // Çarpışma dinleyicileri
    private val collisionListeners = mutableListOf<CollisionListener>()
    
    // Son çarpışmalar
    private val lastCollisions = mutableSetOf<Pair<GameObject, GameObject>>()
    
    /**
     * Tüm oyun nesneleri arasındaki çarpışmaları kontrol eder
     */
    fun checkCollisions(gameObjects: List<GameObject>) {
        // Mevcut çarpışmaları temizle
        val currentCollisions = mutableSetOf<Pair<GameObject, GameObject>>()
        
        // Tüm nesne çiftlerini kontrol et
        for (i in gameObjects.indices) {
            for (j in i + 1 until gameObjects.size) {
                val obj1 = gameObjects[i]
                val obj2 = gameObjects[j]
                
                // Çarpışma kontrolü
                if (checkCollision(obj1, obj2)) {
                    // Çarpışma tespit edildi
                    val collision = Pair(obj1, obj2)
                    currentCollisions.add(collision)
                    
                    // Yeni bir çarpışma mı?
                    if (!lastCollisions.contains(collision)) {
                        // Çarpışma başladı olayını tetikle
                        notifyCollisionStart(obj1, obj2)
                    }
                }
            }
        }
        
        // Sona eren çarpışmaları bul
        for (collision in lastCollisions) {
            if (!currentCollisions.contains(collision)) {
                // Çarpışma sona erdi olayını tetikle
                notifyCollisionEnd(collision.first, collision.second)
            }
        }
        
        // Son çarpışmaları güncelle
        lastCollisions.clear()
        lastCollisions.addAll(currentCollisions)
    }
    
    /**
     * İki nesne arasında çarpışma olup olmadığını kontrol eder
     */
    private fun checkCollision(obj1: GameObject, obj2: GameObject): Boolean {
        // Dikdörtgen çarpışma kontrolü (AABB)
        return obj1.getX() < obj2.getX() + obj2.getWidth() &&
               obj1.getX() + obj1.getWidth() > obj2.getX() &&
               obj1.getY() < obj2.getY() + obj2.getHeight() &&
               obj1.getY() + obj1.getHeight() > obj2.getY()
    }
    
    /**
     * Çarpışma başladı olayını tetikler
     */
    private fun notifyCollisionStart(obj1: GameObject, obj2: GameObject) {
        for (listener in collisionListeners) {
            listener.onCollisionStart(obj1, obj2)
        }
    }
    
    /**
     * Çarpışma sona erdi olayını tetikler
     */
    private fun notifyCollisionEnd(obj1: GameObject, obj2: GameObject) {
        for (listener in collisionListeners) {
            listener.onCollisionEnd(obj1, obj2)
        }
    }
    
    /**
     * Çarpışma dinleyicisi ekler
     */
    fun addCollisionListener(listener: CollisionListener) {
        collisionListeners.add(listener)
    }
    
    /**
     * Çarpışma dinleyicisini kaldırır
     */
    fun removeCollisionListener(listener: CollisionListener) {
        collisionListeners.remove(listener)
    }
    
    /**
     * Tüm çarpışma dinleyicilerini temizler
     */
    fun clearCollisionListeners() {
        collisionListeners.clear()
    }
    
    /**
     * Çarpışma dinleyicisi arayüzü
     */
    interface CollisionListener {
        fun onCollisionStart(obj1: GameObject, obj2: GameObject)
        fun onCollisionEnd(obj1: GameObject, obj2: GameObject)
    }
    
    /**
     * Çarpışma verilerini temizler
     */
    fun reset() {
        lastCollisions.clear()
    }
}
