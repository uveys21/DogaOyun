package com.dogakasifleri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.CharacterOption

/**
 * Karakter Adapter - RecyclerView'da karakter seçeneklerini göstermek için kullanılır
 */
class CharacterAdapter(
    private val characters: List<CharacterOption>,
    private val onItemClick: (CharacterOption) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val currentCharacter = characters[position]
        holder.bind(currentCharacter)
    }

    override fun getItemCount(): Int = characters.size

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCharacterImage: ImageView = itemView.findViewById(R.id.ivCharacterImage)
        private val tvCharacterName: TextView = itemView.findViewById(R.id.tvCharacterName)

        fun bind(character: CharacterOption) {
            ivCharacterImage.setImageResource(character.imageResId)
            tvCharacterName.text = character.name

            // Öğe tıklama olayı
            itemView.setOnClickListener {
                onItemClick(character)
            }
        }
    }
}
