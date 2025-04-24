package com.dogakasifleri.game.minigames

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dogakasifleri.game.engine.GameEngine
import com.dogakasifleri.models.MiniGame

/**
 * Mini Oyun Yöneticisi - Mini oyunları yönetir
 */
class MiniGameManager(private val context: Context?) {
    
    // Mevcut mini oyunlar
    private val miniGames = mutableListOf<MiniGame>()
    
    // Aktif mini oyun
    private var activeMiniGame: BaseMiniGame? = null
    
    // Mini oyun tamamlama dinleyicisi
    private var completionListener: MiniGameCompletionListener? = null
    
    init {
        // Mini oyunları yükle
        loadMiniGames()
    }
    
    /**
     * Mini oyunları yükler
     */
    private fun loadMiniGames() {
        miniGames.add(
            MiniGame(
                id = 1,
                name = "Hayvan Hafıza Oyunu",
                description = "Hayvanları eşleştirerek hafızanı test et",
                type = MiniGame.GameType.MEMORY,
                difficulty = MiniGame.GameDifficulty.EASY,
                ecosystemId = null, // Tüm ekosistemler
                imageResource = 0,
                rewardPoints = 10
            )
        )
        
        miniGames.add(
            MiniGame(
                id = 2,
                name = "Orman Yapbozu",
                description = "Orman ekosistemini keşfet",
                type = MiniGame.GameType.PUZZLE,
                difficulty = MiniGame.GameDifficulty.MEDIUM,
                ecosystemId = 1, // Orman
                imageResource = 0,
                rewardPoints = 15
            )
        )
        
        miniGames.add(
            MiniGame(
                id = 3,
                name = "Okyanus Bilgi Yarışması",
                description = "Okyanus hakkında ne kadar bilgiye sahipsin?",
                type = MiniGame.GameType.QUIZ,
                difficulty = MiniGame.GameDifficulty.MEDIUM,
                ecosystemId = 2, // Okyanus
                imageResource = 0,
                rewardPoints = 20
            )
        )
        
        miniGames.add(
            MiniGame(
                id = 4,
                name = "Çöl Hayvanlarını Sırala",
                description = "Çöl hayvanlarını boyutlarına göre sırala",
                type = MiniGame.GameType.SORTING,
                difficulty = MiniGame.GameDifficulty.HARD,
                ecosystemId = 3, // Çöl
                imageResource = 0,
                rewardPoints = 25
            )
        )
        
        miniGames.add(
            MiniGame(
                id = 5,
                name = "Geri Dönüşüm Oyunu",
                description = "Atıkları doğru geri dönüşüm kutularına yerleştir",
                type = MiniGame.GameType.RECYCLING,
                difficulty = MiniGame.GameDifficulty.EASY,
                ecosystemId = null, // Tüm ekosistemler
                imageResource = 0,
                rewardPoints = 15
            )
        )
    }
    
    /**
     * Tüm mini oyunları döndürür
     * 
     * @return Mini oyunlar listesi
     */
    fun getAllMiniGames(): List<MiniGame> {
        return miniGames
    }
    
    /**
     * Belirli bir ekosisteme ait mini oyunları döndürür
     * 
     * @param ecosystemId Ekosistem ID'si, null ise tüm ekosistemler
     * @return Mini oyunlar listesi
     */
    fun getMiniGamesByEcosystem(ecosystemId: Int?): List<MiniGame> {
        return if (ecosystemId == null) {
            miniGames
        } else {
            miniGames.filter { it.ecosystemId == ecosystemId || it.ecosystemId == null }
        }
    }
    
    /**
     * Belirli bir zorluğa sahip mini oyunları döndürür
     * 
     * @param difficulty Zorluk seviyesi
     * @return Mini oyunlar listesi
     */
    fun getMiniGamesByDifficulty(difficulty: MiniGame.GameDifficulty): List<MiniGame> {
        return miniGames.filter { it.difficulty == difficulty }
    }
    
    /**
     * Belirli bir tipe sahip mini oyunları döndürür
     * 
     * @param type Oyun tipi
     * @return Mini oyunlar listesi
     */
    fun getMiniGamesByType(type: MiniGame.GameType): List<MiniGame> {
        return miniGames.filter { it.type == type }
    }
    
    /**
     * Mini oyunu başlatır
     * 
     * @param gameId Oyun ID'si
     * @return Oyun görünümü, başarısızsa null
     */
    fun startMiniGame(gameId: Int): SurfaceView? {
        val miniGame = miniGames.find { it.id == gameId } ?: return null
        
        activeMiniGame = when (miniGame.type) {
            MiniGame.GameType.MEMORY -> MemoryGame(context, miniGame)
            MiniGame.GameType.PUZZLE -> PuzzleGame(context, miniGame)
            MiniGame.GameType.QUIZ -> QuizGame(context, miniGame)
            MiniGame.GameType.SORTING -> SortingGame(context, miniGame)
            MiniGame.GameType.RECYCLING -> RecyclingGame(context, miniGame)
        }
        
        activeMiniGame?.setCompletionListener(completionListener)
        return activeMiniGame
    }
    
    /**
     * Mini oyunu durdurur
     */
    fun stopMiniGame() {
        activeMiniGame?.stopGame()
        activeMiniGame = null
    }
    
    /**
     * Mini oyun tamamlama dinleyicisini ayarlar
     * 
     * @param listener Dinleyici
     */
    fun setCompletionListener(listener: MiniGameCompletionListener) {
        completionListener = listener
        activeMiniGame?.setCompletionListener(listener)
    }
    
    /**
     * Mini oyun tamamlama dinleyicisi arayüzü
     */
    interface MiniGameCompletionListener {
        /**
         * Mini oyun tamamlandığında çağrılır
         * 
         * @param gameId Oyun ID'si
         * @param score Skor
         */
        fun onMiniGameCompleted(gameId: Int, score: Int)
    }
    
    /**
     * Temel Mini Oyun sınıfı - Tüm mini oyunlar için temel sınıf
     */
    abstract class BaseMiniGame(
        context: Context,
        protected val miniGame: MiniGame
    ) : SurfaceView(context), SurfaceHolder.Callback {
        
        protected var gameEngine: GameEngine = GameEngine(context)
        protected var completionListener: MiniGameCompletionListener? = null
        protected var score: Int = 0
        
        init {
            holder.addCallback(this)
            isFocusable = true
        }
        
        /**
         * Oyunu başlatır
         */
        abstract fun startGame()
        
        /**
         * Oyunu durdurur
         */
        abstract fun stopGame()
        
        /**
         * Oyunu sıfırlar
         */
        abstract fun resetGame()
        
        /**
         * Tamamlama dinleyicisini ayarlar
         * 
         * @param listener Dinleyici
         */
        fun setCompletionListener(listener: MiniGameCompletionListener?) {
            completionListener = listener
        }
        
        /**
         * Oyunu tamamlar
         */
        protected fun completeGame() {
            completionListener?.onMiniGameCompleted(miniGame.id, score)
        }
        
        override fun surfaceCreated(holder: SurfaceHolder) {
            // Oyunu başlat
            startGame()
        }
        
        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            // Boş implementasyon
        }
        
        override fun surfaceDestroyed(holder: SurfaceHolder) {
            // Oyunu durdur
            stopGame()
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Alt sınıflar tarafından override edilecek
            return super.onTouchEvent(event)
        }
    }
    
    /**
     * Hafıza Oyunu - Eşleştirme oyunu
     */
    private class MemoryGame(
        context: Context,
        miniGame: MiniGame
    ) : BaseMiniGame(context, miniGame) {
        
        override fun startGame() {
            // Oyun başlatma kodları
        }
        
        override fun stopGame() {
            // Oyun durdurma kodları
        }
        
        override fun resetGame() {
            // Oyun sıfırlama kodları
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Dokunma olayı işleme kodları
            return true
        }
    }
    
    /**
     * Yapboz Oyunu - Parçaları birleştirme oyunu
     */
    private class PuzzleGame(
        context: Context,
        miniGame: MiniGame
    ) : BaseMiniGame(context, miniGame) {
        
        override fun startGame() {
            // Oyun başlatma kodları
        }
        
        override fun stopGame() {
            // Oyun durdurma kodları
        }
        
        override fun resetGame() {
            // Oyun sıfırlama kodları
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Dokunma olayı işleme kodları
            return true
        }
    }
    
    /**
     * Bilgi Yarışması - Soru-cevap oyunu
     */
    private class QuizGame(
        context: Context,
        miniGame: MiniGame
    ) : BaseMiniGame(context, miniGame) {
        
        override fun startGame() {
            // Oyun başlatma kodları
        }
        
        override fun stopGame() {
            // Oyun durdurma kodları
        }
        
        override fun resetGame() {
            // Oyun sıfırlama kodları
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Dokunma olayı işleme kodları
            return true
        }
    }
    
    /**
     * Sıralama Oyunu - Nesneleri sıralama oyunu
     */
    private class SortingGame(
        context: Context,
        miniGame: MiniGame
    ) : BaseMiniGame(context, miniGame) {
        
        override fun startGame() {
            // Oyun başlatma kodları
        }
        
        override fun stopGame() {
            // Oyun durdurma kodları
        }
        
        override fun resetGame() {
            // Oyun sıfırlama kodları
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Dokunma olayı işleme kodları
            return true
        }
    }
    
    /**
     * Geri Dönüşüm Oyunu - Atıkları doğru kutulara yerleştirme oyunu
     */
    private class RecyclingGame(
        context: Context,
        miniGame: MiniGame
    ) : BaseMiniGame(context, miniGame) {
        
        override fun startGame() {
            // Oyun başlatma kodları
        }
        
        override fun stopGame() {
            // Oyun durdurma kodları
        }
        
        override fun resetGame() {
            // Oyun sıfırlama kodları
        }
        
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Dokunma olayı işleme kodları
            return true
        }
    }
}
