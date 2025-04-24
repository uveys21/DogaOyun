package com.dogakasifleri.models

import java.io.Serializable

/**
 * CollectionItem - Kullanıcının koleksiyonundaki öğeleri temsil eden model sınıfı
 * Bu sınıf, koleksiyondaki hayvan, bitki veya diğer doğa öğelerinin bilgilerini içerir.
 */
data class CollectionItem(
    val id: Int,
    val name: String,
    val type: String, // Animal, Plant, Fossil, Rock, etc.
    val imageUrl: String,
    val description: String,
    val ecosystemType: String,
    val discoveryDate: Long,
    val rarity: String, // Common, Uncommon, Rare, Epic, Legendary
    val referenceId: Int, // Animal veya Plant ID'si
    val isFavorite: Boolean = false,
    val notes: String = "",
    val tags: List<String> = listOf()
) : Serializable {
    
    /**
     * Koleksiyon öğesinin nadirlik seviyesine göre renk kodunu döndürür
     */
    fun getRarityColor(): String {
        return when (rarity) {
            "Common" -> "#A0A0A0" // Gri
            "Uncommon" -> "#00C000" // Yeşil
            "Rare" -> "#0070DD" // Mavi
            "Epic" -> "#A335EE" // Mor
            "Legendary" -> "#FF8000" // Turuncu
            else -> "#A0A0A0"
        }
    }
    
    /**
     * Koleksiyon öğesinin tipini Türkçe olarak döndürür
     */
    fun getTypeText(): String {
        return when (type) {
            "Animal" -> "Hayvan"
            "Plant" -> "Bitki"
            "Fossil" -> "Fosil"
            "Rock" -> "Kaya"
            "Shell" -> "Kabuk"
            "Feather" -> "Tüy"
            else -> type
        }
    }
    
    /**
     * Koleksiyon öğesinin keşif tarihini formatlanmış olarak döndürür
     */
    fun getFormattedDiscoveryDate(): String {
        val date = java.util.Date(discoveryDate)
        val format = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
        return format.format(date)
    }
    
    /**
     * Koleksiyon öğesinin ekosistem tipine göre arka plan resmini döndürür
     */
    fun getEcosystemBackground(): String {
        return when (ecosystemType) {
            "Orman" -> "bg_ecosystem_forest.xml"
            "Okyanus" -> "bg_ecosystem_ocean.xml"
            "Çöl" -> "bg_ecosystem_desert.xml"
            "Kutup" -> "bg_ecosystem_arctic.xml"
            else -> "bg_ecosystem_forest.xml"
        }
    }
    
    /**
     * Koleksiyon öğesinin nadirlik seviyesini Türkçe olarak döndürür
     */
    fun getRarityText(): String {
        return when (rarity) {
            "Common" -> "Yaygın"
            "Uncommon" -> "Az Yaygın"
            "Rare" -> "Nadir"
            "Epic" -> "Epik"
            "Legendary" -> "Efsanevi"
            else -> rarity
        }
    }
    
    /**
     * Koleksiyon öğesinin etiketlerini formatlanmış olarak döndürür
     */
    fun getFormattedTags(): String {
        if (tags.isEmpty()) return ""
        return tags.joinToString(", ")
    }
}
