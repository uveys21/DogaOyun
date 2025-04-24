package com.dogakasifleri.game.engine

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dogakasifleri.models.Character
import com.dogakasifleri.utils.AnimationUtils
import com.dogakasifleri.utils.SoundManager

/**
 * GameEngine - Oyun motorunun ana sınıfı
 * Bu sınıf, oyun döngüsünü yönetir ve diğer oyun bileşenlerini koordine eder.
 */
class GameEngine(
    private val context: Context,
    private val surfaceHolder: SurfaceHolder,
    private val surfaceView: SurfaceView
) {
    // Oyun durumu
    private var isRunning = false
    private var isPaused = false
    
    // Oyun bileşenleri
    private val physicsEngine = PhysicsEngine()
    private val collisionDetector = CollisionDetector()
    private val animationEngine = AnimationEngine(context)
    private val soundManager = SoundManager(context)
    
    // Oyun nesneleri
    private var character: Character? = null
    private val gameObjects = mutableListOf<GameObject>()
    
    // Oyun döngüsü thread'i
    private var gameThread: Thread? = null
    
    // FPS hesaplama
    private var fps = 0
    private var lastTime = System.currentTimeMillis()
    private var frameCount = 0
    
    /**
     * Oyunu başlatır
     */
    fun start() {
        if (isRunning) return
        
        isRunning = true
        isPaused = false
        
        // Oyun thread'ini başlat
        gameThread = Thread(gameLoop)
        gameThread?.start()
        
        // Arka plan müziğini başlat
        soundManager.playMusic("game_background")
    }
    
    /**
     * Oyunu duraklatır
     */
    fun pause() {
        isPaused = true
        soundManager.pauseMusic()
    }
    
    /**
     * Oyunu devam ettirir
     */
    fun resume() {
        isPaused = false
        soundManager.resumeMusic()
    }
    
    /**
     * Oyunu durdurur
     */
    fun stop() {
        isRunning = false
        
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        
        soundManager.stopMusic()
    }
    
    /**
     * Oyun döngüsü
     */
    private val gameLoop = Runnable {
        var canvas: Canvas?
        
        while (isRunning) {
            if (!isPaused) {
                canvas = null
                
                try {
                    canvas = surfaceHolder.lockCanvas()
                    synchronized(surfaceHolder) {
                        update()
                        render(canvas)
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }
                
                // FPS hesapla
                calculateFPS()
            }
        }
    }
    
    /**
     * Oyun durumunu günceller
     */
    private fun update() {
        // Fizik motorunu güncelle
        physicsEngine.update(gameObjects)
        
        // Çarpışmaları kontrol et
        collisionDetector.checkCollisions(gameObjects)
        
        // Animasyonları güncelle
        animationEngine.update()
        
        // Oyun nesnelerini güncelle
        for (gameObject in gameObjects) {
            gameObject.update()
        }
    }
    
    /**
     * Oyunu ekrana çizer
     */
    private fun render(canvas: Canvas) {
        // Arka planı çiz
        canvas.drawRGB(255, 255, 255)
        
        // Oyun nesnelerini çiz
        for (gameObject in gameObjects) {
            gameObject.render(canvas)
        }
        
        // Animasyonları çiz
        animationEngine.render(canvas)
        
        // FPS'i çiz
        canvas.drawText("FPS: $fps", 10f, 20f, android.graphics.Paint())
    }
    
    /**
     * FPS hesaplar
     */
    private fun calculateFPS() {
        frameCount++
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastTime
        
        if (elapsedTime >= 1000) {
            fps = frameCount
            frameCount = 0
            lastTime = currentTime
        }
    }
    
    /**
     * Karakteri ayarlar
     */
    fun setCharacter(character: Character) {
        this.character = character
        
        // Karakter nesnesini oluştur ve oyun nesnelerine ekle
        val characterObject = CharacterObject(character)
        gameObjects.add(characterObject)
    }
    
    /**
     * Oyun nesnesini ekler
     */
    fun addGameObject(gameObject: GameObject) {
        gameObjects.add(gameObject)
    }
    
    /**
     * Oyun nesnesini kaldırır
     */
    fun removeGameObject(gameObject: GameObject) {
        gameObjects.remove(gameObject)
    }
    
    /**
     * Tüm oyun nesnelerini temizler
     */
    fun clearGameObjects() {
        gameObjects.clear()
    }
    
    /**
     * Ses efekti çalar
     */
    fun playSound(soundName: String) {
        soundManager.playSound(soundName)
    }
    
    /**
     * Oyun nesnesi arayüzü
     */
    interface GameObject {
        fun update()
        fun render(canvas: Canvas)
        fun getX(): Float
        fun getY(): Float
        fun getWidth(): Float
        fun getHeight(): Float
    }
    
    /**
     * Karakter nesnesi
     */
    inner class CharacterObject(private val character: Character) : GameObject {
        private var x = 0f
        private var y = 0f
        private val width = 100f
        private val height = 100f
        private val speed = 5f
        
        override fun update() {
            // Karakter hareketini güncelle
        }
        
        override fun render(canvas: Canvas) {
            // Karakteri çiz
            val paint = android.graphics.Paint()
            paint.color = android.graphics.Color.BLUE
            canvas.drawRect(x, y, x + width, y + height, paint)
        }
        
        override fun getX(): Float = x
        override fun getY(): Float = y
        override fun getWidth(): Float = width
        override fun getHeight(): Float = height
        
        fun moveLeft() {
            x -= speed
        }
        
        fun moveRight() {
            x += speed
        }
        
        fun moveUp() {
            y -= speed
        }
        
        fun moveDown() {
            y += speed
        }
        
        fun jump() {
            // Zıplama fiziğini uygula
            physicsEngine.applyJump(this)
        }
    }
}
