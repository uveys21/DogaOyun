package com.dogakasifleri.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.AchievementAdapter
import com.dogakasifleri.models.Achievement
import com.dogakasifleri.utils.AchievementManager
import com.dogakasifleri.utils.AnimationUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

/**
 * AchievementsActivity - Kullanıcının kazandığı başarıları görüntüleyebileceği aktivite
 * Bu aktivite, kullanıcının oyun içinde tamamladığı görevler ve etkinlikler sonucunda
 * kazandığı başarı rozetlerini gösterir.
 */
class AchievementsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AchievementAdapter
    private lateinit var achievementManager: AchievementManager
    private var achievements: List<Achievement> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
        
        // Toolbar ayarları
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Başarılarım"
        
        // Başarı yöneticisini başlat
        achievementManager = AchievementManager(this)
        
        // Başarıları yükle
        loadAchievements()
        
        // RecyclerView ayarları
        recyclerView = findViewById(R.id.recyclerViewAchievements)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AchievementAdapter(this@AchievementsActivity, achievements)
        recyclerView.adapter = adapter
        
        // Başarı istatistiklerini göster
        updateAchievementStats()
    }
    
    /**
     * Başarıları yükler
     */
    private fun loadAchievements() {
        // Tüm başarıları al (kazanılmış ve kazanılmamış)
        achievements = achievementManager.getAllAchievements(this)
        
        // Başarı durumunu kontrol et
        if (achievements.isEmpty()) {
            // Boş başarı durumunu göster
            findViewById<View>(R.id.emptyAchievementsView).visibility = View.VISIBLE
        } else {
            findViewById<View>(R.id.emptyAchievementsView).visibility = View.GONE
        }
    }
    
    /**
     * Başarı istatistiklerini günceller
     */
    private fun updateAchievementStats() {
        val earnedAchievements = achievements.count { it.isUnlocked }
        val totalAchievements = achievements.size

        
        // İstatistikleri göster
        findViewById<TextView>(R.id.textViewAchievementStats).text = 
            "Toplam $earnedAchievements / $totalAchievements başarı kazandın"
        
        // İlerleme çubuğunu güncelle
        val progressBar = findViewById<ProgressBar>(R.id.progressBarAchievements)
        progressBar.max = totalAchievements
        progressBar.progress = earnedAchievements
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        // Geri dönüş animasyonu
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}
