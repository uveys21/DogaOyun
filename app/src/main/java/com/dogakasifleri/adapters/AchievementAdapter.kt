package com.dogakasifleri.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.Achievement

/**
 * Başarı Adapter - RecyclerView'da başarıları ve rozetleri göstermek için kullanılır
 */
class AchievementAdapter(
    private val context: Context,
    private val achievements: List<Achievement>
) : RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_achievement, parent, false)
        return AchievementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val currentAchievement = achievements[position]
        holder.bind(currentAchievement)
    }

    override fun getItemCount(): Int = achievements.size

    inner class AchievementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivAchievementIcon: ImageView = itemView.findViewById(R.id.ivAchievementIcon)
        private val tvAchievementTitle: TextView = itemView.findViewById(R.id.tvAchievementTitle)
        private val tvAchievementDescription: TextView = itemView.findViewById(R.id.tvAchievementDescription)
        private val ivLockStatus: ImageView = itemView.findViewById(R.id.ivLockStatus)

        fun bind(achievement: Achievement) {
            ivAchievementIcon.setImageResource(achievement.iconResId)
            tvAchievementTitle.text = achievement.title
            tvAchievementDescription.text = achievement.description
            
            // Başarının kilidinin açık olup olmadığını göster
            if (achievement.isUnlocked) {
                ivLockStatus.setImageResource(R.drawable.ic_unlocked)
                // Kilidi açık başarılar için normal görünüm
                itemView.alpha = 1.0f
            } else {
                ivLockStatus.setImageResource(R.drawable.ic_locked)
                // Kilidi kapalı başarılar için soluk görünüm
                itemView.alpha = 0.6f
            }
        }
    }
}
