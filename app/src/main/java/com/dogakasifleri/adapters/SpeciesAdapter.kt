package com.dogakasifleri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.Species

/**
 * Tür Adapter - RecyclerView'da türleri göstermek için kullanılır
 */
class SpeciesAdapter(
    private val species: List<Species>,
    private val onItemClick: (Species) -> Unit
) : RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_species, parent, false)
        return SpeciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val currentSpecies = species[position]
        holder.bind(currentSpecies)
    }

    override fun getItemCount(): Int = species.size

    inner class SpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivSpeciesImage: ImageView = itemView.findViewById(R.id.ivSpeciesImage)
        private val tvSpeciesName: TextView = itemView.findViewById(R.id.tvSpeciesName)
        private val tvSpeciesDescription: TextView = itemView.findViewById(R.id.tvSpeciesDescription)

        fun bind(species: Species) {
            ivSpeciesImage.setImageResource(species.imageResId)
            tvSpeciesName.text = species.name
            tvSpeciesDescription.text = species.description

            // Öğe tıklama olayı
            itemView.setOnClickListener {
                onItemClick(species)
            }
        }
    }
}
