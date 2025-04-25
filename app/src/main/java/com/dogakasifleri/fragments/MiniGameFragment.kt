package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dogakasifleri.R
import com.dogakasifleri.game.minigames.MiniGameManager
import com.dogakasifleri.models.MiniGame
import com.dogakasifleri.utils.AnimationUtils
import com.dogakasifleri.viewmodels.MiniGameViewModel

/**
 * MiniGameFragment - Mini oyunları gösteren ve oynatan fragment
 * Bu fragment, çocukların eğlenerek öğrenmesini sağlayan çeşitli mini oyunları içerir.
 * Hafıza oyunu, bulmaca, quiz, sıralama ve geri dönüşüm oyunları gibi çeşitli oyunlar sunulur.
 */
class MiniGameFragment : Fragment() {

    private lateinit var viewModel: MiniGameViewModel
    private lateinit var miniGameManager: MiniGameManager
    private var currentGame: MiniGame? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mini_game, container, false)
        
        // Mini oyun ID'sini al
        val gameId = arguments?.getInt("GAME_ID", -1) ?: -1
        
        // ViewModel'i başlat
        viewModel = ViewModelProvider(this).get(MiniGameViewModel::class.java)
        
        // Mini oyun yöneticisini başlat
        miniGameManager = MiniGameManager(requireContext())
        
        // Mini oyunu yükle
        loadMiniGame(gameId)
        
        // Oyun kontrol butonlarını ayarla
        setupGameControls(view)
        
        // Animasyonları uygula
        AnimationUtils.applyGameAnimations(view.findViewById(R.id.gameContainer))
        
        return view
    }
    
    /**
     * Mini oyunu yükler
     */
    private fun loadMiniGame(gameId: Int) {
        if (gameId == -1) {
            // Varsayılan oyunu göster (oyun seçim ekranı)
            showGameSelectionScreen()
            return
        }
        
        // ViewModel'den mini oyunu al
        viewModel.getMiniGame(gameId).observe(viewLifecycleOwner, { result ->
            currentGame = result
            
            // Oyunu başlat
            startGame(currentGame)
        })
    }
    
    /**
     * Oyun seçim ekranını gösterir
     */
    private fun showGameSelectionScreen() {
        // Tüm oyunları al
        viewModel.getAllMiniGames().observe(viewLifecycleOwner, { games ->
            // Oyun seçim ekranını göster
            view?.findViewById<View>(R.id.gameSelectionLayout)?.visibility = View.VISIBLE
            view?.findViewById<View>(R.id.gameContainer)?.visibility = View.GONE
            
            // Oyun butonlarını ayarla
            setupGameButtons(games)
        })
    }
    
    /**
     * Oyun butonlarını ayarlar
     */
    private fun setupGameButtons(games: List<MiniGame>) {
        val gameButtonsContainer = view?.findViewById<LinearLayout>(R.id.gameButtonsContainer)
        gameButtonsContainer?.removeAllViews()
        
        for (game in games) {
            val button = Button(context)
            button.text = game.name
            button.setBackgroundResource(getGameButtonBackground(game.type))
            
            // Buton tıklama olayını ayarla
            button.setOnClickListener {
                // Oyunu başlat
                currentGame = game
                startGame(game)
                
                // Oyun seçim ekranını gizle
                view?.findViewById<View>(R.id.gameSelectionLayout)?.visibility = View.GONE
                view?.findViewById<View>(R.id.gameContainer)?.visibility = View.VISIBLE
            }
            
            // Butonu container'a ekle
            gameButtonsContainer?.addView(button)
        }
    }
    
    /**
     * Oyun tipine göre buton arka planını döndürür
     */
    private fun getGameButtonBackground(gameType: String): Int {
        return when (gameType) {
            "Hafıza" -> R.drawable.bg_button_blue
            "Bulmaca" -> R.drawable.bg_button_green
            "Quiz" -> R.drawable.bg_button_orange
            "Sıralama" -> R.drawable.bg_button_purple
            "Geri Dönüşüm" -> R.drawable.bg_button_green
            else -> R.drawable.bg_button_blue
        }
    }
    
    /**
     * Oyunu başlatır
     */
    private fun startGame(game: MiniGame?) {
        val container = view?.findViewById<ViewGroup>(R.id.gameContainer)
        when (game?.type) {
            "Hafıza" -> miniGameManager.startMemoryGame(container, game)
            "Bulmaca" -> miniGameManager.startPuzzleGame(container, game)
            "Quiz" -> miniGameManager.startQuizGame(container, game)
            "Sıralama" -> miniGameManager.startSortingGame(container, game)
            "Geri Dönüşüm" -> miniGameManager.startRecyclingGame(container, game)
        }
    }
    /**
     * Oyun kontrol butonlarını ayarlar
     */
    private fun setupGameControls(view: View) {
        // Geri butonu
        view.findViewById<Button>(R.id.buttonBack).setOnClickListener {
            // Oyun seçim ekranına dön
            showGameSelectionScreen()
        }
        
        // Yeniden başlat butonu
        view.findViewById<Button>(R.id.buttonRestart).setOnClickListener {
            // Oyunu yeniden başlat
            startGame(currentGame)
        }
        
        // İpucu butonu
        view.findViewById<Button>(R.id.buttonHint).setOnClickListener {
            // İpucu göster
            miniGameManager.showHint()
        }
    }


    
    companion object {
        fun newInstance(gameId: Int): MiniGameFragment {
            val fragment = MiniGameFragment()
            val args = Bundle()
            args.putInt("GAME_ID", gameId)
            fragment.arguments = args
            return fragment
        }
    }
}
