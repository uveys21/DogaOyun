package com.dogakasifleri.game.minigames

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.dogakasifleri.game.engine.GameEngine
import com.dogakasifleri.models.MiniGame

/**
 * Mini Oyun Yöneticisi - Mini oyunları yönetir
 */
class MiniGameManager(private val context: Context) {
    private var activeGame: BaseMiniGame? = null

    fun startMemoryGame(container: ViewGroup?, game: MiniGame) {
        container?.removeAllViews()
        activeGame = MemoryGame(context, game).apply {
            container?.addView(this)
            startGame()
        }
    }

    fun startPuzzleGame(container: ViewGroup?, game: MiniGame) {
        container?.removeAllViews()
        activeGame = PuzzleGame(context, game).apply {
            container?.addView(this)
            startGame()
        }
    }

    fun startQuizGame(container: ViewGroup?, game: MiniGame) {
        container?.removeAllViews()
        activeGame = QuizGame(context, game).apply {
            container?.addView(this)
            startGame()
        }
    }

    fun startSortingGame(container: ViewGroup?, game: MiniGame) {
        container?.removeAllViews()
        activeGame = SortingGame(context, game).apply {
            container?.addView(this)
            startGame()
        }
    }

    fun startRecyclingGame(container: ViewGroup?, game: MiniGame) {
        container?.removeAllViews()
        activeGame = RecyclingGame(context, game).apply {
            container?.addView(this)
            startGame()
        }
    }

    fun showHint() {
        activeGame?.showHint()
    }

    abstract inner class BaseMiniGame(context: Context, val game: MiniGame) : SurfaceView(context) {
        abstract fun startGame()
        abstract fun showHint()
    }

    private inner class MemoryGame(context: Context, game: MiniGame) : BaseMiniGame(context, game) {
        override fun startGame() { /* Implement memory game logic */ }
        override fun showHint() { /* Show memory game hint */ }
    }

    // Diğer oyun sınıflarını benzer şekilde implement edin
}
