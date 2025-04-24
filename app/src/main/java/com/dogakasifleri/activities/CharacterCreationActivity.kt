package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.CharacterAdapter
import com.dogakasifleri.models.CharacterOption

/**
 * Karakter oluşturma ekranı - Kullanıcı kendi karakterini özelleştirir
 */
class CharacterCreationActivity : AppCompatActivity() {

    private lateinit var rvAvatars: RecyclerView
    private lateinit var etCharacterName: TextView
    private lateinit var btnSaveCharacter: Button
    private lateinit var ivSelectedAvatar: ImageView
    
    private var selectedAvatarId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_creation)

        // UI elemanlarını tanımla
        rvAvatars = findViewById(R.id.rvAvatars)
        etCharacterName = findViewById(R.id.etCharacterName)
        btnSaveCharacter = findViewById(R.id.btnSaveCharacter)
        ivSelectedAvatar = findViewById(R.id.ivSelectedAvatar)

        // Avatar seçeneklerini hazırla
        setupAvatarOptions()

        // Karakter kaydetme butonu tıklama olayı
        btnSaveCharacter.setOnClickListener {
            saveCharacter()
        }
    }

    private fun setupAvatarOptions() {
        // Örnek avatar seçenekleri
        val avatarOptions = listOf(
            CharacterOption(1, "Kaşif Kız", R.drawable.avatar_1, "Kaşif Kız Avatarı"),
            CharacterOption(2, "Kaşif Erkek", R.drawable.avatar_2, "Kaşif Erkek Avatarı"),
            CharacterOption(3, "Bilim İnsanı Kız", R.drawable.avatar_3, "Bilim İnsanı Kız Avatarı"),
            CharacterOption(4, "Bilim İnsanı Erkek", R.drawable.avatar_4, "Bilim İnsanı Erkek Avatarı"),
            CharacterOption(5, "Doğa Koruyucusu Kız", R.drawable.avatar_5, "Doğa Koruyucusu Kız Avatarı"),
            CharacterOption(6, "Doğa Koruyucusu Erkek", R.drawable.avatar_6, "Doğa Koruyucusu Erkek Avatarı")
        )

        // Adapter'ı ayarla
        val adapter = CharacterAdapter(avatarOptions) { characterOption ->
            // Avatar seçildiğinde
            selectedAvatarId = characterOption.id
            ivSelectedAvatar.setImageResource(characterOption.imageResId)
        }

        // RecyclerView'ı ayarla
        rvAvatars.layoutManager = GridLayoutManager(this, 3)
        rvAvatars.adapter = adapter
    }

    private fun saveCharacter() {
        val characterName = etCharacterName.text.toString()
        
        if (characterName.isBlank()) {
            etCharacterName.error = "Lütfen bir isim girin"
            return
        }

        if (selectedAvatarId == 0) {
            // Kullanıcıya avatar seçmesi gerektiğini bildir
            // Gerçek uygulamada bir Toast veya Snackbar gösterilebilir
            return
        }

        // Karakter bilgilerini kaydet
        // Gerçek uygulamada SharedPreferences veya veritabanına kaydedilebilir
        
        // Ana ekrana geçiş yap
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
