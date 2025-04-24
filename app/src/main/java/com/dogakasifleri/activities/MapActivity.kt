package com.dogakasifleri.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.EcosystemAdapter
import com.dogakasifleri.models.Ecosystem

/**
 * Harita ekranı - Farklı ekosistemlerin seçildiği ekran
 */
class MapActivity : AppCompatActivity() {

    private lateinit var rvEcosystems: RecyclerView
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // UI elemanlarını tanımla
        rvEcosystems = findViewById(R.id.rvEcosystems)
        btnBack = findViewById(R.id.btnBack)

        // Geri butonu tıklama olayı
        btnBack.setOnClickListener {
            finish()
        }

        // Ekosistem seçeneklerini hazırla
        setupEcosystems()
    }

    private fun setupEcosystems() {
        // Örnek ekosistem seçenekleri
        val ecosystems = listOf(
            Ecosystem(
                1,
                "Orman",
                R.drawable.ecosystem_forest,
                "Ağaçlar, bitkiler ve orman hayvanlarını keşfet",
                R.color.forest_green,
                0
            ),
            Ecosystem(
                2,
                "Okyanus",
                R.drawable.ecosystem_ocean,
                "Deniz canlıları ve okyanus dünyasını keşfet",
                R.color.ocean_blue,
                0
            ),
            Ecosystem(
                3,
                "Çöl",
                R.drawable.ecosystem_desert,
                "Çöl bitkileri ve hayvanlarını keşfet",
                R.color.desert_yellow,
                0
            ),
            Ecosystem(
                4,
                "Kutuplar",
                R.drawable.ecosystem_arctic,
                "Kutup hayvanları ve buzul dünyasını keşfet",
                R.color.arctic_blue,
                0
            )
        )

        // Adapter'ı ayarla
        val adapter = EcosystemAdapter(ecosystems) { ecosystem ->
            // Ekosistem seçildiğinde
            openEcosystem(ecosystem)
        }

        // RecyclerView'ı ayarla
        rvEcosystems.layoutManager = GridLayoutManager(this, 2)
        rvEcosystems.adapter = adapter
    }

    private fun openEcosystem(ecosystem: Ecosystem) {
        // Seçilen ekosisteme göre EcosystemActivity'yi başlat
        val intent = android.content.Intent(this, EcosystemActivity::class).apply {
            putExtra("ECOSYSTEM_ID", ecosystem.id)
            putExtra("ECOSYSTEM_NAME", ecosystem.name)
            putExtra("ECOSYSTEM_COLOR", ecosystem.colorResId)
        }
        startActivity(intent)
    }
}
