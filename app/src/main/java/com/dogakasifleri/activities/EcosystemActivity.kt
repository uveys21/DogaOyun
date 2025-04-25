package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.SpeciesAdapter
import com.dogakasifleri.models.Species

/**
 * Ekosistem ekranı - Seçilen ekosistemdeki türleri gösterir
 */
class EcosystemActivity : AppCompatActivity() {

    private lateinit var ivEcosystemBanner: ImageView
    private lateinit var tvEcosystemTitle: TextView
    private lateinit var tvEcosystemDescription: TextView
    private lateinit var rvSpecies: RecyclerView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecosystem)

        // UI elemanlarını tanımla
        ivEcosystemBanner = findViewById(R.id.ivEcosystemBanner)
        tvEcosystemTitle = findViewById(R.id.tvEcosystemTitle)
        tvEcosystemDescription = findViewById(R.id.tvEcosystemDescription)
        rvSpecies = findViewById(R.id.rvSpecies)
        btnBack = findViewById(R.id.btnBack)

        // Intent'ten ekosistem bilgilerini al
        val ecosystemId = intent.getIntExtra("ECOSYSTEM_ID", 0)
        val ecosystemName = intent.getStringExtra("ECOSYSTEM_NAME") ?: "Ekosistem"
        val ecosystemColorName = intent.getStringExtra("ECOSYSTEM_COLOR") ?: "forest_green"

        val ecosystemColor = when (ecosystemColorName) {
            "forest_green" -> R.color.forest_green
            "ocean_blue" -> R.color.ocean_blue
            "desert_yellow" -> R.color.desert_yellow
            "arctic_white" -> R.color.arctic_white
            else -> R.color.forest_green // Default to forest_green if not found
        }


        // Ekosistem bilgilerini göster
        setupEcosystemDetails(ecosystemId, ecosystemName, ecosystemColor)

        // Ekosistem arkaplan rengini ayarla
        val colorInt = ContextCompat.getColor(this, ecosystemColor)
        tvEcosystemTitle.setBackgroundColor(colorInt)


        // Geri butonu tıklama olayı
        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupEcosystemDetails(
        ecosystemId: Int,
        ecosystemName: String,
        ecosystemColor: Int
    ) {
        // Ekosistem başlığını ayarla
        tvEcosystemTitle.text = ecosystemName


        // Ekosistem banner resmini ayarla
        when (ecosystemId) {
            1 -> { // Orman
                ivEcosystemBanner.setImageResource(R.drawable.banner_forest)
                tvEcosystemDescription.text = getString(R.string.forest_description)
                setupForestSpecies()
            }

            2 -> { // Okyanus
                ivEcosystemBanner.setImageResource(R.drawable.banner_ocean)
                tvEcosystemDescription.text = getString(R.string.ocean_description)
                setupOceanSpecies()
            }

            3 -> { // Çöl
                ivEcosystemBanner.setImageResource(R.drawable.banner_desert)
                tvEcosystemDescription.text = getString(R.string.desert_description)
                setupDesertSpecies()
            }

            4 -> { // Kutuplar
                ivEcosystemBanner.setImageResource(R.drawable.banner_arctic)
                tvEcosystemDescription.text = getString(R.string.arctic_description)
                setupArcticSpecies()
            }
        }
    }

    private fun setupForestSpecies() {
        // Orman türleri
        val species = listOf(
            Species(
                1,
                "Meşe Ağacı",
                "Quercus robur",
                "Orman",
                "Ormanın en güçlü ağacı",
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
                "Çevik ve sevimli kemirgen",
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
                "Ormanın görkemli otçulu",
                R.drawable.species_deer,
                "Geyikler, ormanlarda yaşayan otçul hayvanlardır. Erkek geyiklerin boynuzları vardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                4,
                "Mantar",
                "Fungi",
                "Orman",
                "Ormanın gizemli organizması",
                R.drawable.species_mushroom,
                "Mantarlar, ormanlarda yetişen ve çürükçül beslenen organizmalardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            )
        )
        setupSpeciesRecyclerView(species)
    }

    private fun setupOceanSpecies() {
        // Okyanus türleri
        val species = listOf(
            Species(
                5,
                "Yunus",
                "Delphinus delphis",
                "Orman",
                "Okyanusun zeki memelisi",
                R.drawable.species_dolphin,
                "Yunuslar, zeki deniz memelileridir. Gruplar halinde yaşar ve ekolokatör kullanarak avlanırlar.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                6,
                "Mercan",
                "Anthozoa",
                "Orman",
                "Okyanusun renkli sakini",
                R.drawable.species_coral,
                "Mercanlar, sıcak ve sığ sularda yaşayan koloniler halindeki deniz canlılarıdır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                7,
                "Deniz Yıldızı",
                "Asteroidea",
                "Orman",
                "Okyanusun garip canlısı",
                R.drawable.species_starfish,
                "Deniz yıldızları, beş kollu deniz canlılarıdır. Kopan uzuvlarını yenileyebilirler.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                8,
                "Ahtapot",
                "Octopoda",
                "Orman",
                "Okyanusun zeki avcısı",
                R.drawable.species_octopus,
                "Ahtapotlar, sekiz kollu ve çok zeki deniz canlılarıdır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            )
        )
        setupSpeciesRecyclerView(species)
    }

    private fun setupDesertSpecies() {
        // Çöl türleri
        val species = listOf(
            Species(
                9,
                "Kaktüs",
                "Cactaceae",
                "Orman",
                "Çölün su deposu",
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
                "Çölün büyük kulaklısı",
                R.drawable.species_desert_fox,
                "Çöl tilkileri, büyük kulaklara sahip ve geceleri aktif olan hayvanlardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                11,
                "Akrep",
                "Scorpiones",
                "Orman",
                "Çölün zehirli eklem bacaklısı",
                R.drawable.species_scorpion,
                "Akrepler, zehirli iğneleri olan eklem bacaklılardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                12,
                "Deve",
                "Camelus",
                "Orman",
                "Çölün dayanıklı yolcusu",
                R.drawable.species_camel,
                "Develer, hörgüçlerinde yağ depolayarak uzun süre susuz kalabilen hayvanlardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            )
        )
        setupSpeciesRecyclerView(species)
    }

    private fun setupArcticSpecies() {
        // Kutup türleri
        val species = listOf(
            Species(
                13,
                "Kutup Ayısı",
                "Ursus maritimus",
                "Orman",
                "Kutupun dev avcısı",
                R.drawable.species_polar_bear,
                "Kutup ayıları, kalın kürkleri sayesinde soğuk iklimlerde yaşayabilen büyük memelilerdir.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                14,
                "Penguen",
                "Spheniscidae",
                "Orman",
                "Kutupun sevimli kuşu",
                R.drawable.species_penguin,
                "Penguenler, uçamayan ama çok iyi yüzen kuşlardır.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            ),
            Species(
                15,
                "Fok",
                "Phocidae",
                "Orman",
                "Kutupun sualtı sakini",
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
                "Kutupun boynuzlu geyiği",
                R.drawable.species_reindeer,
                "Ren geyikleri, kutup bölgelerinde yaşayan ve hem erkekleri hem de dişileri boynuz taşıyan geyiklerdir.",
                "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.",
                1,
                0
            )
        )
        setupSpeciesRecyclerView(species)
    }

    private fun setupSpeciesRecyclerView(species: List<Species>) {
        // Adapter'ı ayarla
        val adapter = SpeciesAdapter(species) { selectedSpecies ->
            // Tür seçildiğinde
            openSpeciesDetail(selectedSpecies)
        }

        // RecyclerView'ı ayarla
        rvSpecies.layoutManager = LinearLayoutManager(this)
        rvSpecies.adapter = adapter
    }

    private fun openSpeciesDetail(species: Species) {
        // Seçilen tür için detay ekranını aç
        val intent = Intent(this, SpeciesDetailActivity::class.java)
        intent.putExtra("SPECIES_ID", species.id)
        intent.putExtra("SPECIES_NAME", species.name)
        intent.putExtra("SPECIES_IMAGE", species.imageResId)
        intent.putExtra("SPECIES_DESCRIPTION", species.description)
        startActivity(intent)
    }

}