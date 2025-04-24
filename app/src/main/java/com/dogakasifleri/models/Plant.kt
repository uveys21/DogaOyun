package com.dogakasifleri.models

import java.io.Serializable

/**
 * Plant - Bitki türlerini temsil eden model sınıfı
 * Bu sınıf, bitkilerin özellikleri, yaşam alanları ve faydaları hakkında bilgileri içerir.
 */
data class Plant(
    val id: Int,
    val name: String,
    val scientificName: String,
    val ecosystemType: String,
    val description: String,
    val imageUrl: String,
    val animationUrl: String = "",
    val conservationStatus: String = "LC", // Least Concern (IUCN)
    val plantType: String, // Tree, Flower, Grass, Shrub, etc.
    val lifespan: String,
    val height: String,
    val habitat: String,
    val uses: List<String> = listOf(),
    val funFacts: List<String> = listOf(),
    val isDiscovered: Boolean = false,
    val discoveryDate: Long = 0,
    val rarity: String = "Common" // Common, Uncommon, Rare, Epic, Legendary
) : Serializable {
    
    /**
     * Bitkinin koruma durumunu açıklayan metni döndürür
     */
    fun getConservationStatusDescription(): String {
        return when (conservationStatus) {
            "EX" -> "Soyu Tükenmiş (Extinct)"
            "EW" -> "Doğada Soyu Tükenmiş (Extinct in the Wild)"
            "CR" -> "Kritik Tehlikede (Critically Endangered)"
            "EN" -> "Tehlikede (Endangered)"
            "VU" -> "Hassas (Vulnerable)"
            "NT" -> "Tehdide Yakın (Near Threatened)"
            "LC" -> "Asgari Endişe (Least Concern)"
            "DD" -> "Yetersiz Veri (Data Deficient)"
            "NE" -> "Değerlendirilmemiş (Not Evaluated)"
            else -> "Bilinmiyor"
        }
    }
    
    /**
     * Bitkinin türünü açıklayan metni döndürür
     */
    fun getPlantTypeDescription(): String {
        return when (plantType) {
            "Tree" -> "Ağaç"
            "Flower" -> "Çiçek"
            "Grass" -> "Çimen/Ot"
            "Shrub" -> "Çalı"
            "Herb" -> "Bitki/Ot"
            "Moss" -> "Yosun"
            "Fern" -> "Eğrelti Otu"
            "Cactus" -> "Kaktüs"
            "Algae" -> "Alg"
            else -> plantType
        }
    }
    
    /**
     * Bitkinin nadirlik seviyesine göre puan değerini döndürür
     */
    fun getRarityPoints(): Int {
        return when (rarity) {
            "Common" -> 10
            "Uncommon" -> 25
            "Rare" -> 50
            "Epic" -> 100
            "Legendary" -> 250
            else -> 10
        }
    }
    
    /**
     * Bitkinin nadirlik seviyesine göre renk kodunu döndürür
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
     * Bitkinin ekosistem tipine göre arka plan resmini döndürür
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
     * Bitkinin kullanım alanlarını açıklayan metni döndürür
     */
    fun getUsesDescription(): String {
        if (uses.isEmpty()) return "Bu bitkinin bilinen özel bir kullanımı yoktur."
        return uses.joinToString(", ")
    }
    
    /**
     * Bitkinin rastgele bir ilginç bilgisini döndürür
     */
    fun getRandomFunFact(): String {
        if (funFacts.isEmpty()) return "Bu bitki hakkında ilginç bir bilgi bulunmuyor."
        return funFacts.random()
    }
}
