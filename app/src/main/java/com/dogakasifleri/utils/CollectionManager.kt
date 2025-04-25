package com.dogakasifleri.utils

import android.content.Context
import androidx.core.content.edit
import com.dogakasifleri.models.CollectionItem

/**
 * Koleksiyon Yöneticisi - Kullanıcının keşfettiği türleri yönetir
 */
class CollectionManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("Collection", Context.MODE_PRIVATE)

    /**
     * Koleksiyona tür ekler
     */
    fun addToCollection(speciesId: Int) {
        sharedPreferences.edit() {
            putBoolean("species_$speciesId", true)
        }
    }

    /**
     * Koleksiyondan tür çıkarır
     */
    fun removeFromCollection(speciesId: Int) {
        val editor = sharedPreferences.edit()
        editor.remove("species_$speciesId")
        editor.apply()
    }

    /**
     * Türün koleksiyonda olup olmadığını kontrol eder
     */
    fun isInCollection(speciesId: Int): Boolean {
        val collectedItems = getCollectedSpecies()
        return collectedItems.any { it.speciesId == speciesId }

    }

    /**
     * Koleksiyondaki tüm türlerin ID'lerini döndürür
     */
    fun getCollectedSpecies(): MutableList<CollectionItem> {
        val collectedItems = mutableListOf<CollectionItem>()

        // Tüm kayıtları kontrol et
        val allEntries = sharedPreferences.all

        for (entry in allEntries) {
            // Tür ID'lerini ayıkla
            if (entry.key.startsWith("species_") && entry.value == true) {
                val speciesId = entry.key.removePrefix("species_").toIntOrNull()
                if (speciesId != null) {
                    collectedItems.add(CollectionItem(speciesId))
                }
            }
        }

        return collectedSpecies
    }

    /**
     * Koleksiyondaki tür sayısını döndürür
     */
    fun getCollectionCount(): Int {
        return getCollectedSpecies().count()
    }

    /**
     * Koleksiyonu temizler
     */
    fun clearCollection() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
