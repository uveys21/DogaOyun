package com.dogakasifleri.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dogakasifleri.R
import com.dogakasifleri.models.CollectionItem

/**
 * Koleksiyon Adapter - RecyclerView'da koleksiyondaki öğeleri göstermek için kullanılır
 * CollectionItem modelini kullanır.
 */
class CollectionAdapter(
    private val context: Context,
    private var items: MutableList<CollectionItem>,
    private val onItemClick: (CollectionItem) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            // item_collection.xml layout'unu inflate ediyoruz
            .inflate(R.layout.item_collection, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = items.size

    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // item_collection.xml'deki ID'ler kullanılarak View'lar bulunur
        private val ivItemImage: ImageView = itemView.findViewById(R.id.ivSpeciesImage) // XML ID'si güncellendi
        private val tvItemName: TextView = itemView.findViewById(R.id.tvSpeciesName) // XML ID'si güncellendi
        // Eğer layout'ta başka view'lar varsa (tip, nadirlik vb.) onları da burada tanımlayın

        fun bind(item: CollectionItem) {
            // TextView'a CollectionItem'dan alınan isim atanır
            tvItemName.text = item.name

            // Resim Yükleme (Glide ile)
            val imageResource = getImageResourceIdFromString(item.imageUrl)
            if (imageResource != 0) {
                Glide.with(context)
                    .load(imageResource) // Drawable ID'si yüklendi
                    .placeholder(R.drawable.placeholder_image) // Placeholder resmi
                    .error(R.drawable.error_image) // Hata resmi
                    .centerCrop() // Resmin nasıl sığdırılacağı
                    .into(ivItemImage) // Hedef ImageView
            } else {
                Log.w("CollectionAdapter", "Kaynak bulunamadı: ${item.imageUrl}")
                ivItemImage.setImageResource(R.drawable.error_image) // Varsayılan resim
            }

            // Tıklama olayı
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        // String'i drawable ID'sine çeviren yardımcı fonksiyon
        private fun getImageResourceIdFromString(imageName: String?): Int {
            if (imageName.isNullOrBlank()) return 0
            val cleanName = imageName.substringBeforeLast(".")
            val resourceId = context.resources.getIdentifier(cleanName, "drawable", context.packageName)
            return resourceId
        }
    }

    /**
     * Adapter'ın listesini yeni verilerle günceller.
     */
    fun updateData(newItems: List<CollectionItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
        Log.d("CollectionAdapter", "Adapter data updated with ${newItems.size} items.")
    }
}