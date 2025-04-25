package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.CollectionAdapter
import com.dogakasifleri.models.CollectionItem
import com.dogakasifleri.models.Species
import com.dogakasifleri.utils.CollectionManager
import com.dogakasifleri.adapters.CollectionAdapter

/**
 * Koleksiyon Fragment - Kullanıcının keşfettiği türleri gösterir
 */
class CollectionFragment : Fragment() {

    private lateinit var rvCollection: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unu inflate et
        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        // UI elemanlarını tanımla
        rvCollection = view.findViewById(R.id.rvCollection)

        // Koleksiyon öğelerini yükle
        loadCollection()

        return view
    }

    override fun onResume() {
        super.onResume()
        // Fragment her görünür olduğunda koleksiyonu güncelle
        loadCollection()
    }

    private fun loadCollection() {
        // Koleksiyon yöneticisini oluştur
        val collectionManager = CollectionManager(requireContext())
        
        // Koleksiyondaki CollectionItem'ları al
        val collectedItems = collectionManager.getCollectedSpecies()
        
        // Tüm türleri al
        val allSpecies = getAllSpecies()

        // Adapter'ı ayarla
        val adapter = CollectionAdapter(requireContext(), collectedItems) { collectionItem ->
            // Tür seçildiğinde detay ekranını aç
            openSpeciesDetail(collectionItem)

        }

        // RecyclerView'ı ayarla
        rvCollection.layoutManager = GridLayoutManager(requireContext(), 2)
        rvCollection.adapter = adapter
    }

    private fun getAllSpecies(): MutableList<Species> {
        // Tüm türleri döndür
        return mutableListOf(
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

    private fun openSpeciesDetail(collectionItem: CollectionItem) {
<<<<<<< Updated upstream
        // Tür detay ekranını aç
=======
         // Tür detay ekranını aç
>>>>>>> Stashed changes
        val selectedSpecies = getAllSpecies().find { it.id == collectionItem.speciesId }
        val intent = android.content.Intent(requireContext(), com.dogakasifleri.activities.SpeciesDetailActivity::class.java)
        if (selectedSpecies != null){
            intent.putExtra("SPECIES_ID", selectedSpecies.id)
            intent.putExtra("SPECIES_NAME", selectedSpecies.name)
            intent.putExtra("SPECIES_IMAGE", selectedSpecies.imageResId)
            intent.putExtra("SPECIES_DESCRIPTION", selectedSpecies.description)
        }

        startActivity(intent)
    }

}
