package com.dogakasifleri.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.CollectionItem
import com.dogakasifleri.models.Species

/**
 * Koleksiyon Adapter - RecyclerView'da koleksiyondaki öğeleri göstermek için kullanılır
 */
class CollectionAdapter(
    private val context: Context,
    private val items: MutableList<CollectionItem>,
    private val onItemClick: (CollectionItem) -> Unit
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection, parent, false)
        return CollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(items[position])
    }
    

    fun updateData(newItems: List<CollectionItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // Verinin değiştiğini RecyclerView'a bildirir.
    }


    override fun getItemCount(): Int = items.size


    inner class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivSpeciesImage: ImageView = itemView.findViewById(R.id.ivSpeciesImage)

        fun bind(item: CollectionItem) {
            val allSpecies = getAllSpecies()
            val species = allSpecies.find{ it.id == item.speciesId }

            if (species != null) {
                ivSpeciesImage.setImageResource(species.imageResId)
            } else {
                ivSpeciesImage.setImageResource(R.drawable.error_image)
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        private fun getAllSpecies(): List<Species> {
            // Tüm türleri döndür
            return listOf(
                // Orman türleri
                Species(
                    1,
                    "Meşe Ağacı",
                    "Quercus robur",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_oak_tree,
                    "Meşe ağaçları, güçlü ve uzun ömürlü ağaçlardır. Palamut adı verilen meyveler üretirler.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    2,
                    "Sincap",
                    "Sciurus vulgaris",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_squirrel,
                    "Sincaplar, ağaçlarda yaşayan ve fındık, tohum gibi besinlerle beslenen kemirgenlerdir.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    3,
                    "Geyik",
                    "Cervus elaphus",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_deer,
                    "Geyikler, ormanlarda yaşayan otçul hayvanlardır. Erkek geyiklerin boynuzları vardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    4,
                    "Mantar",
                    "Agaricus bisporus",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_mushroom,
                    "Mantarlar, ormanlarda yetişen ve çürükçül beslenen organizmalardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),

                // Okyanus türleri
                Species(
                    5,
                    "Yunus",
                    "Delphinus delphis",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_dolphin,
                    "Yunuslar, zeki deniz memelileridir. Gruplar halinde yaşar ve ekolokatör kullanarak avlanırlar.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    6,
                    "Mercan",
                    "Corallium rubrum",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_coral,
                    "Mercanlar, sıcak ve sığ sularda yaşayan koloniler halindeki deniz canlılarıdır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    7,
                    "Deniz Yıldızı",
                    "Asterias rubens",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_starfish,
                    "Deniz yıldızları, beş kollu deniz canlılarıdır. Kopan uzuvlarını yenileyebilirler.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    8,
                    "Ahtapot",
                    "Octopus vulgaris",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_octopus,
                    "Ahtapotlar, sekiz kollu ve çok zeki deniz canlılarıdır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),

                // Çöl türleri
                Species(
                    9,
                    "Kaktüs",
                    "Carnegiea gigantea",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_cactus,
                    "Kaktüsler, çöl ortamına uyum sağlamış, su depolayabilen bitkilerdir.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    10,
                    "Çöl Tilkisi",
                    "Vulpes zerda",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_desert_fox,
                    "Çöl tilkileri, büyük kulaklara sahip ve geceleri aktif olan hayvanlardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    11,
                    "Akrep",
                    "Androctonus australis",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_scorpion,
                    "Akrepler, zehirli iğneleri olan eklem bacaklılardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    12,
                    "Deve",
                    "Camelus dromedarius",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_camel,
                    "Develer, hörgüçlerinde yağ depolayarak uzun süre susuz kalabilen hayvanlardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),

                // Kutup türleri
                Species(
                    13,
                    "Kutup Ayısı",
                    "Ursus maritimus",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_polar_bear,
                    "Kutup ayıları, kalın kürkleri sayesinde soğuk iklimlerde yaşayabilen büyük memelilerdir.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    14,
                    "Penguen",
                    "Aptenodytes forsteri",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_penguin,
                    "Penguenler, uçamayan ama çok iyi yüzen kuşlardır.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    15,
                    "Fok",
                    "Phoca vitulina",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_seal,
                    "Foklar, hem karada hem de suda yaşayabilen deniz memelileridir.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                ),
                Species(
                    16,
                    "Ren Geyiği",
                    "Rangifer tarandus",
                    "Orman",
                    "Yağmur ormanlarının en güçlü avcısı",
                    R.drawable.species_reindeer,
                    "Ren geyikleri, kutup bölgelerinde yaşayan ve hem erkekleri hem de dişileri boynuz taşıyan geyiklerdir.",
                    "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                    1,
                    0
                )
            )
        }
    }
}
