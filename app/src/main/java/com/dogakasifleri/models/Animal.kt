package com.dogakasifleri.models

import java.io.Serializable

/**
 * Animal - Hayvan türlerini temsil eden model sınıfı
 * Bu sınıf, hayvanların özellikleri, yaşam alanları ve davranışları hakkında bilgileri içerir.
 */
data class Animal(
    val id: Int,
    val name: String,
    val scientificName: String,
    val ecosystemType: String,
    val description: String,
    val imageUrl: String,
    val soundUrl: String = "",
    val animationUrl: String = "",
    val conservationStatus: String = "LC", // Least Concern (IUCN)
    val diet: String,
    val lifespan: String,
    val size: String,
    val habitat: String,
    val funFacts: List<String> = listOf(),
    val isDiscovered: Boolean = false,
    val discoveryDate: Long = 0,
    val rarity: String = "Common" // Common, Uncommon, Rare, Epic, Legendary
) : Serializable {
    
    /**
     * Hayvanın koruma durumunu açıklayan metni döndürür
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
     * Hayvanın beslenme şeklini açıklayan metni döndürür
     */
    fun getDietDescription(): String {
        return when (diet) {
            "Herbivore" -> "Otçul (Bitkilerle beslenir)"
            "Carnivore" -> "Etçil (Diğer hayvanlarla beslenir)"
            "Omnivore" -> "Hepçil (Hem bitki hem hayvanlarla beslenir)"
            "Insectivore" -> "Böcekçil (Böceklerle beslenir)"
            "Frugivore" -> "Meyveci (Meyvelerle beslenir)"
            "Nectarivore" -> "Nektarcı (Çiçek nektarıyla beslenir)"
            else -> diet
        }
    }
    
    /**
     * Hayvanın nadirlik seviyesine göre puan değerini döndürür
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
     * Hayvanın nadirlik seviyesine göre renk kodunu döndürür
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
     * Hayvanın ekosistem tipine göre arka plan resmini döndürür
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
     * Hayvanın rastgele bir ilginç bilgisini döndürür
     */
    fun getRandomFunFact(): String {
        if (funFacts.isEmpty()) return "Bu hayvan hakkında ilginç bir bilgi bulunmuyor."
        return funFacts.random()
    }
}
