package com.dogakasifleri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.Ecosystem

/**
 * Ekosistem Adapter - RecyclerView'da ekosistemleri göstermek için kullanılır
 */
class EcosystemAdapter(
    private val ecosystems: List<Ecosystem>,
    private val onItemClick: (Ecosystem) -> Unit
) : RecyclerView.Adapter<EcosystemAdapter.EcosystemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcosystemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ecosystem, parent, false)
        return EcosystemViewHolder(view)
    }

    override fun onBindViewHolder(holder: EcosystemViewHolder, position: Int) {
        val currentEcosystem = ecosystems[position]
        holder.bind(currentEcosystem)
    }

    override fun getItemCount(): Int = ecosystems.size

    inner class EcosystemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivEcosystemImage: ImageView = itemView.findViewById(R.id.ivEcosystemImage)
        private val tvEcosystemName: TextView = itemView.findViewById(R.id.tvEcosystemName)
        private val tvEcosystemDescription: TextView = itemView.findViewById(R.id.tvEcosystemDescription)

        fun bind(ecosystem: Ecosystem) {
            ivEcosystemImage.setImageResource(ecosystem.imageResId)
            tvEcosystemName.text = ecosystem.name
            tvEcosystemDescription.text = ecosystem.description
            
            // Başlık arkaplan rengini ayarla
            tvEcosystemName.setBackgroundResource(ecosystem.colorResId)

            // Öğe tıklama olayı
            itemView.setOnClickListener {
                onItemClick(ecosystem)
            }
        }
    }
}
