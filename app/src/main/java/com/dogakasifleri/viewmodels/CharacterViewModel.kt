package com.dogakasifleri.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dogakasifleri.models.Character
import com.dogakasifleri.models.CharacterOption

/**
 * CharacterViewModel - Karakter oluşturma ve düzenleme verilerini yöneten ViewModel sınıfı
 * Bu sınıf, kullanıcının karakter oluşturma ve düzenleme ekranlarında kullanılan
 * verileri yönetir ve UI bileşenlerine sunar.
 */
class CharacterViewModel : ViewModel() {

    private val _character = MutableLiveData<Character>()
    val character: LiveData<Character> = _character

    private val _avatarOptions = MutableLiveData<List<CharacterOption>>()
    val avatarOptions: LiveData<List<CharacterOption>> = _avatarOptions

    private val _hairStyleOptions = MutableLiveData<List<CharacterOption>>()
    val hairStyleOptions: LiveData<List<CharacterOption>> = _hairStyleOptions

    private val _hairColorOptions = MutableLiveData<List<CharacterOption>>()
    val hairColorOptions: LiveData<List<CharacterOption>> = _hairColorOptions

    private val _skinToneOptions = MutableLiveData<List<CharacterOption>>()
    val skinToneOptions: LiveData<List<CharacterOption>> = _skinToneOptions

    private val _eyeColorOptions = MutableLiveData<List<CharacterOption>>()
    val eyeColorOptions: LiveData<List<CharacterOption>> = _eyeColorOptions

    private val _clothesColorOptions = MutableLiveData<List<CharacterOption>>()
    val clothesColorOptions: LiveData<List<CharacterOption>> = _clothesColorOptions

    private val _accessoryOptions = MutableLiveData<List<CharacterOption>>()
    val accessoryOptions: LiveData<List<CharacterOption>> = _accessoryOptions

    private val _specialAbilityOptions = MutableLiveData<List<CharacterOption>>()
    val specialAbilityOptions: LiveData<List<CharacterOption>> = _specialAbilityOptions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Karakter oluşturma seçeneklerini yükler
     */
    fun loadCharacterOptions() {
        _isLoading.value = true
        
        try {
            // Avatar seçeneklerini yükle
            loadAvatarOptions()
            
            // Saç stili seçeneklerini yükle
            loadHairStyleOptions()
            
            // Saç rengi seçeneklerini yükle
            loadHairColorOptions()
            
            // Ten rengi seçeneklerini yükle
            loadSkinToneOptions()
            
            // Göz rengi seçeneklerini yükle
            loadEyeColorOptions()
            
            // Kıyafet rengi seçeneklerini yükle
            loadClothesColorOptions()
            
            // Aksesuar seçeneklerini yükle
            loadAccessoryOptions()
            
            // Özel yetenek seçeneklerini yükle
            loadSpecialAbilityOptions()
            
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Karakter seçenekleri yüklenirken hata oluştu: ${e.message}"
            _isLoading.value = false
        }
    }
    
    /**
     * Avatar seçeneklerini yükler
     */
    private fun loadAvatarOptions() {
        val options = listOf(
            CharacterOption(1, "Avatar 1", "avatars/avatar_1.png", "Standart avatar"),
            CharacterOption(2, "Avatar 2", "avatars/avatar_2.png", "Kaşif avatar"),
            CharacterOption(3, "Avatar 3", "avatars/avatar_3.png", "Bilim insanı avatar"),
            CharacterOption(4, "Avatar 4", "avatars/avatar_4.png", "Doğa uzmanı avatar")
        )
        
        _avatarOptions.value = options
    }
    
    /**
     * Saç stili seçeneklerini yükler
     */
    private fun loadHairStyleOptions() {
        val options = listOf(
            CharacterOption(1, "Kısa", "hairstyles/short.png", "Kısa saç stili"),
            CharacterOption(2, "Uzun", "hairstyles/long.png", "Uzun saç stili"),
            CharacterOption(3, "Kıvırcık", "hairstyles/curly.png", "Kıvırcık saç stili"),
            CharacterOption(4, "Örgülü", "hairstyles/braided.png", "Örgülü saç stili")
        )
        
        _hairStyleOptions.value = options
    }
    
    /**
     * Saç rengi seçeneklerini yükler
     */
    private fun loadHairColorOptions() {
        val options = listOf(
            CharacterOption(1, "Siyah", "colors/black.png", "#000000"),
            CharacterOption(2, "Kahverengi", "colors/brown.png", "#8B4513"),
            CharacterOption(3, "Sarı", "colors/blonde.png", "#FFD700"),
            CharacterOption(4, "Kızıl", "colors/red.png", "#FF4500"),
            CharacterOption(5, "Mavi", "colors/blue.png", "#1E90FF"),
            CharacterOption(6, "Yeşil", "colors/green.png", "#32CD32"),
            CharacterOption(7, "Mor", "colors/purple.png", "#8A2BE2")
        )
        
        _hairColorOptions.value = options
    }
    
    /**
     * Ten rengi seçeneklerini yükler
     */
    private fun loadSkinToneOptions() {
        val options = listOf(
            CharacterOption(1, "Açık", "skintones/light.png", "#FFE0BD"),
            CharacterOption(2, "Orta", "skintones/medium.png", "#D8AD7F"),
            CharacterOption(3, "Bronz", "skintones/tan.png", "#C68642"),
            CharacterOption(4, "Kahverengi", "skintones/brown.png", "#8D5524"),
            CharacterOption(5, "Koyu", "skintones/dark.png", "#5C3317")
        )
        
        _skinToneOptions.value = options
    }
    
    /**
     * Göz rengi seçeneklerini yükler
     */
    private fun loadEyeColorOptions() {
        val options = listOf(
            CharacterOption(1, "Kahverengi", "colors/brown.png", "#8B4513"),
            CharacterOption(2, "Mavi", "colors/blue.png", "#1E90FF"),
            CharacterOption(3, "Yeşil", "colors/green.png", "#32CD32"),
            CharacterOption(4, "Ela", "colors/hazel.png", "#D2B48C"),
            CharacterOption(5, "Gri", "colors/gray.png", "#708090"),
            CharacterOption(6, "Amber", "colors/amber.png", "#FFBF00")
        )
        
        _eyeColorOptions.value = options
    }
    
    /**
     * Kıyafet rengi seçeneklerini yükler
     */
    private fun loadClothesColorOptions() {
        val options = listOf(
            CharacterOption(1, "Kırmızı", "colors/red.png", "#FF0000"),
            CharacterOption(2, "Mavi", "colors/blue.png", "#0000FF"),
            CharacterOption(3, "Yeşil", "colors/green.png", "#008000"),
            CharacterOption(4, "Sarı", "colors/yellow.png", "#FFFF00"),
            CharacterOption(5, "Mor", "colors/purple.png", "#800080"),
            CharacterOption(6, "Turuncu", "colors/orange.png", "#FFA500"),
            CharacterOption(7, "Pembe", "colors/pink.png", "#FFC0CB")
        )
        
        _clothesColorOptions.value = options
    }
    
    /**
     * Aksesuar seçeneklerini yükler
     */
    private fun loadAccessoryOptions() {
        val options = listOf(
            CharacterOption(0, "Yok", "accessories/none.png", "Aksesuar yok"),
            CharacterOption(1, "Şapka", "accessories/hat.png", "Kaşif şapkası"),
            CharacterOption(2, "Gözlük", "accessories/glasses.png", "Bilim insanı gözlüğü"),
            CharacterOption(3, "Dürbün", "accessories/binoculars.png", "Doğa gözlem dürbünü"),
            CharacterOption(4, "Sırt Çantası", "accessories/backpack.png", "Keşif çantası")
        )
        
        _accessoryOptions.value = options
    }
    
    /**
     * Özel yetenek seçeneklerini yükler
     */
    private fun loadSpecialAbilityOptions() {
        val options = listOf(
            CharacterOption(1, "Kaşif", "abilities/explorer.png", "Haritada daha fazla alan görebilir"),
            CharacterOption(2, "Koleksiyoncu", "abilities/collector.png", "Türleri daha kolay toplayabilir"),
            CharacterOption(3, "Bilim İnsanı", "abilities/scientist.png", "Bilgi testlerinde ipucu alabilir"),
            CharacterOption(4, "Doğa Uzmanı", "abilities/naturalist.png", "Hayvan ve bitkileri daha kolay tanıyabilir")
        )
        
        _specialAbilityOptions.value = options
    }
    
    /**
     * Yeni bir karakter oluşturur
     */
    fun createCharacter(
        name: String,
        avatarId: Int,
        skinTone: String,
        hairColor: String,
        hairStyle: String,
        eyeColor: String,
        clothesColor: String,
        accessoryId: Int,
        specialAbility: String,
        userId: String
    ): Character {
        val character = Character(
            id = System.currentTimeMillis().toInt(),
            name = name,
            avatarId = avatarId,
            skinTone = skinTone,
            hairColor = hairColor,
            hairStyle = hairStyle,
            eyeColor = eyeColor,
            clothesColor = clothesColor,
            accessoryId = accessoryId,
            specialAbility = specialAbility,
            userId = userId
        )
        
        _character.value = character
        return character
    }
    
    /**
     * Mevcut karakteri günceller
     */
    fun updateCharacter(character: Character) {
        _character.value = character
    }
}
