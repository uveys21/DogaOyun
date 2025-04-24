package com.dogakasifleri.models

import java.io.Serializable

/**
 * Character - Kullanıcının oyun içi karakterini temsil eden model sınıfı
 * Bu sınıf, kullanıcının seçtiği karakter özellikleri ve görünümünü içerir.
 */
data class Character(
    val id: Int,
    var name: String,
    var avatarId: Int,
    var skinTone: String,
    var hairColor: String,
    var hairStyle: String,
    var eyeColor: String,
    var clothesColor: String,
    var accessoryId: Int = 0,
    var specialAbility: String = "",
    var userId: String,
    var creationDate: Long = System.currentTimeMillis(),
    var lastModifiedDate: Long = System.currentTimeMillis()
) : Serializable {
    
    /**
     * Karakterin avatar resim yolunu döndürür
     */
    fun getAvatarImagePath(): String {
        return "avatars/avatar_$avatarId.png"
    }
    
    /**
     * Karakterin aksesuar resim yolunu döndürür
     */
    fun getAccessoryImagePath(): String {
        return if (accessoryId > 0) "accessories/accessory_$accessoryId.png" else ""
    }
    
    /**
     * Karakterin özel yeteneğini açıklayan metni döndürür
     */
    fun getSpecialAbilityDescription(): String {
        return when (specialAbility) {
            "explorer" -> "Kaşif: Haritada daha fazla alan görebilir"
            "collector" -> "Koleksiyoncu: Türleri daha kolay toplayabilir"
            "scientist" -> "Bilim İnsanı: Bilgi testlerinde ipucu alabilir"
            "naturalist" -> "Doğa Uzmanı: Hayvan ve bitkileri daha kolay tanıyabilir"
            else -> "Standart karakter"
        }
    }
    
    /**
     * Karakterin saç rengi için RGB değerini döndürür
     */
    fun getHairColorRGB(): String {
        return when (hairColor) {
            "black" -> "#000000"
            "brown" -> "#8B4513"
            "blonde" -> "#FFD700"
            "red" -> "#FF4500"
            "blue" -> "#1E90FF"
            "green" -> "#32CD32"
            "purple" -> "#8A2BE2"
            else -> "#8B4513" // Varsayılan kahverengi
        }
    }
    
    /**
     * Karakterin göz rengi için RGB değerini döndürür
     */
    fun getEyeColorRGB(): String {
        return when (eyeColor) {
            "brown" -> "#8B4513"
            "blue" -> "#1E90FF"
            "green" -> "#32CD32"
            "hazel" -> "#D2B48C"
            "gray" -> "#708090"
            "amber" -> "#FFBF00"
            else -> "#8B4513" // Varsayılan kahverengi
        }
    }
    
    /**
     * Karakterin kıyafet rengi için RGB değerini döndürür
     */
    fun getClothesColorRGB(): String {
        return when (clothesColor) {
            "red" -> "#FF0000"
            "blue" -> "#0000FF"
            "green" -> "#008000"
            "yellow" -> "#FFFF00"
            "purple" -> "#800080"
            "orange" -> "#FFA500"
            "pink" -> "#FFC0CB"
            else -> "#0000FF" // Varsayılan mavi
        }
    }
    
    /**
     * Karakterin ten rengi için RGB değerini döndürür
     */
    fun getSkinToneRGB(): String {
        return when (skinTone) {
            "light" -> "#FFE0BD"
            "medium" -> "#D8AD7F"
            "tan" -> "#C68642"
            "brown" -> "#8D5524"
            "dark" -> "#5C3317"
            else -> "#D8AD7F" // Varsayılan orta ton
        }
    }
}
