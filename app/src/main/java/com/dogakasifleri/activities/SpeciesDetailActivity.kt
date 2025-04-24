package com.dogakasifleri.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.R
import com.dogakasifleri.models.Species
import com.dogakasifleri.utils.CollectionManager

/**
 * Tür detay ekranı - Seçilen türün detaylı bilgilerini gösterir
 */
class SpeciesDetailActivity : AppCompatActivity() {

    private lateinit var ivSpeciesImage: ImageView
    private lateinit var tvSpeciesName: TextView
    private lateinit var tvSpeciesDescription: TextView
    private lateinit var tvSpeciesFacts: TextView
    private lateinit var btnAddToCollection: Button
    private lateinit var btnBack: Button
    private lateinit var btnStartQuiz: Button

    private var speciesId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species_detail)

        // UI elemanlarını tanımla
        ivSpeciesImage = findViewById(R.id.ivSpeciesImage)
        tvSpeciesName = findViewById(R.id.tvSpeciesName)
        tvSpeciesDescription = findViewById(R.id.tvSpeciesDescription)
        tvSpeciesFacts = findViewById(R.id.tvSpeciesFacts)
        btnAddToCollection = findViewById(R.id.btnAddToCollection)
        btnBack = findViewById(R.id.btnBack)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)

        // Intent'ten tür bilgilerini al
        speciesId = intent.getIntExtra("SPECIES_ID", 0)
        val speciesName = intent.getStringExtra("SPECIES_NAME") ?: "Tür"
        val speciesImage = intent.getIntExtra("SPECIES_IMAGE", R.drawable.species_default)
        val speciesDescription = intent.getStringExtra("SPECIES_DESCRIPTION") ?: "Açıklama yok"

        // Tür bilgilerini göster
        tvSpeciesName.text = speciesName
        ivSpeciesImage.setImageResource(speciesImage)
        tvSpeciesDescription.text = speciesDescription
        tvSpeciesFacts.text = getSpeciesFacts(speciesId)

        // Koleksiyona ekle butonu tıklama olayı
        btnAddToCollection.setOnClickListener {
            addToCollection()
        }

        // Geri butonu tıklama olayı
        btnBack.setOnClickListener {
            finish()
        }

        // Quiz başlat butonu tıklama olayı
        btnStartQuiz.setOnClickListener {
            startQuiz()
        }

        // Koleksiyon durumuna göre buton metnini güncelle
        updateCollectionButtonState()
    }

    private fun getSpeciesFacts(speciesId: Int): String {
        // Türe göre ilginç bilgileri döndür
        return when (speciesId) {
            1 -> "• Meşe ağaçları 1000 yıla kadar yaşayabilir.\n• Tek bir meşe ağacı yılda 10 milyon palamut üretebilir.\n• Meşe ağaçları yıldırım çarpmalarına karşı diğer ağaçlardan daha dayanıklıdır."
            2 -> "• Sincaplar koku alma duyularını kullanarak kış için sakladıkları yiyecekleri bulabilirler.\n• Sincapların ön dişleri sürekli uzar.\n• Sincaplar 6 metre yükseklikten düşseler bile zarar görmezler."
            3 -> "• Erkek geyiklerin boynuzları her yıl düşer ve yeniden büyür.\n• Geyikler 80 km/saat hıza ulaşabilirler.\n• Geyikler 1.5 metre yüksekliğe zıplayabilirler."
            4 -> "• Mantarlar ne bitki ne de hayvandır, ayrı bir alemde sınıflandırılırlar.\n• Dünyanın en büyük canlı organizması bir mantardır.\n• Bazı mantar türleri karanlıkta parlayabilir."
            5 -> "• Yunuslar uyurken beyinlerinin sadece yarısını kapatırlar.\n• Yunuslar birbirleriyle konuşmak için 1000'den fazla farklı ses çıkarabilirler.\n• Yunuslar insanlardan sonra dünyanın en zeki canlılarından biridir."
            6 -> "• Mercanlar aslında hayvandır, bitki değildir.\n• Büyük Mercan Resifi, uzaydan görülebilen tek canlı yapıdır.\n• Mercanlar çok yavaş büyür, yılda sadece birkaç santimetre."
            7 -> "• Deniz yıldızları mideleriyle yer, ağızları yoktur.\n• Deniz yıldızları vücutlarının %90'ını kaybetseler bile hayatta kalabilirler.\n• Deniz yıldızlarının kalpleri yoktur."
            8 -> "• Ahtapotların 3 kalbi ve mavi kanı vardır.\n• Ahtapotlar çok zekidir ve problem çözme yetenekleri vardır.\n• Ahtapotlar renk değiştirebilirler."
            9 -> "• Kaktüsler 200 yıla kadar yaşayabilir.\n• Bazı kaktüs türleri 20 metre yüksekliğe ulaşabilir.\n• Kaktüsler suyu gövdelerinde depolar."
            10 -> "• Çöl tilkilerinin büyük kulakları vücut ısılarını düzenlemelerine yardımcı olur.\n• Çöl tilkileri susuz aylarca yaşayabilir.\n• Çöl tilkileri çok iyi duyma yeteneğine sahiptir."
            11 -> "• Akrepler ultraviyole ışık altında parlarlar.\n• Akrepler 6 ay boyunca hiç yemek yemeden yaşayabilirler.\n• Akrepler 450 milyon yıldır dünyada var olan en eski canlılardandır."
            12 -> "• Develer hörgüçlerinde yağ depolar, su değil.\n• Develer günde 190 litre su içebilirler.\n• Develerin üç göz kapağı vardır."
            13 -> "• Kutup ayılarının derisi siyahtır, kürkleri ise şeffaftır.\n• Kutup ayıları 60 km uzaklıktaki bir foku koklayabilirler.\n• Kutup ayıları 10 santimetre kalınlığında yağ tabakasına sahiptir."
            14 -> "• Penguenler tek eşlidir ve aynı eşle hayatları boyunca birlikte kalırlar.\n• Penguenler saatte 36 km hızla yüzebilirler.\n• İmparator penguenleri -60°C sıcaklıkta hayatta kalabilirler."
            15 -> "• Foklar 20 dakika boyunca nefeslerini tutabilirler.\n• Foklar suda uyuyabilirler.\n• Foklar 35 km/saat hızla yüzebilirler."
            16 -> "• Ren geyiklerinin gözleri yazın altın renginden kışın maviye döner.\n• Ren geyikleri burunlarını ısıtmak için özel bir sisteme sahiptir.\n• Ren geyikleri günde 19-24 saat otlarlar."
            else -> "Bu tür hakkında ilginç bilgiler yakında eklenecek."
        }
    }

    private fun addToCollection() {
        // Türü koleksiyona ekle veya çıkar
        val collectionManager = CollectionManager(this)
        
        if (collectionManager.isInCollection(speciesId)) {
            collectionManager.removeFromCollection(speciesId)
        } else {
            collectionManager.addToCollection(speciesId)
        }
        
        // Buton metnini güncelle
        updateCollectionButtonState()
    }

    private fun updateCollectionButtonState() {
        // Koleksiyon durumuna göre buton metnini güncelle
        val collectionManager = CollectionManager(this)
        
        if (collectionManager.isInCollection(speciesId)) {
            btnAddToCollection.text = "Koleksiyondan Çıkar"
        } else {
            btnAddToCollection.text = "Koleksiyona Ekle"
        }
    }

    private fun startQuiz() {
        // Quiz ekranını başlat
        val intent = android.content.Intent(this, QuizActivity::class).apply {
            putExtra("SPECIES_ID", speciesId)
        }
        startActivity(intent)
    }
}
