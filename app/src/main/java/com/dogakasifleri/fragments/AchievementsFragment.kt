package com.dogakasifleri.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.AchievementAdapter
import com.dogakasifleri.models.Achievement
import com.dogakasifleri.utils.AchievementManager

/**
 * Başarılar Fragment - Kullanıcının kazandığı rozetleri gösterir
 */
class AchievementsFragment : Fragment() {

    private lateinit var rvAchievements: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unu inflate et
        val view = inflater.inflate(R.layout.fragment_achievements, container, false)

        // UI elemanlarını tanımla
        rvAchievements = view.findViewById(R.id.rvAchievements)

        // Başarıları yükle
        loadAchievements()

        return view
    }

    override fun onResume() {
        super.onResume()
        // Fragment her görünür olduğunda başarıları güncelle
        loadAchievements()
    }

    private fun loadAchievements() {
        // Başarı yöneticisini oluştur
        val achievementManager = AchievementManager(requireContext())
        
        // Kazanılan başarıları al
        val unlockedAchievements = achievementManager.getUnlockedAchievements()
        
        // Tüm başarıları al
        val allAchievements = getAllAchievements()
        
        // Başarıların kilidinin açık olup olmadığını işaretle
        val achievements = allAchievements.map { achievement ->
            achievement.copy(isUnlocked = achievement.id in unlockedAchievements)
        }
        
        // Adapter'ı ayarla
        val adapter = AchievementAdapter(
            achievements as Context,
            achievements = TODO()
        )
        
        // RecyclerView'ı ayarla
        rvAchievements.layoutManager = LinearLayoutManager(context)
        rvAchievements.adapter = adapter
    }

    private fun getAllAchievements(): List<Achievement> {
        // Tüm başarıları döndür
        return listOf(
            Achievement(
                "explorer",
                "Kaşif",
                "5 farklı türü keşfet",
                R.drawable.achievement_explorer,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "collector",
                "Koleksiyoncu",
                "10 farklı türü koleksiyonuna ekle",
                R.drawable.achievement_collector,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "master_collector",
                "Usta Koleksiyoncu",
                "Tüm türleri koleksiyonuna ekle",
                R.drawable.achievement_master_collector,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "quiz_master",
                "Bilgi Ustası",
                "Bir quizde tüm soruları doğru yanıtla",
                R.drawable.achievement_quiz_master,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "forest_expert",
                "Orman Uzmanı",
                "Ormandaki tüm türleri keşfet",
                R.drawable.achievement_forest_expert,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "ocean_expert",
                "Okyanus Uzmanı",
                "Okyanustaki tüm türleri keşfet",
                R.drawable.achievement_ocean_expert,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "desert_expert",
                "Çöl Uzmanı",
                "Çöldeki tüm türleri keşfet",
                R.drawable.achievement_desert_expert,
                maxProgress = 10,
                progress = 0,
                completed = false
            ),
            Achievement(
                "arctic_expert",
                "Kutup Uzmanı",
                "Kutuplardaki tüm türleri keşfet",
                R.drawable.achievement_arctic_expert,
                maxProgress = 10,
                progress = 0,
                completed = false
            )
        )
    }
}
