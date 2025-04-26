package com.dogakasifleri.utils

import android.content.Context
import android.util.Log
import com.dogakasifleri.R

/**
 * Test yardımcısı - Uygulama bileşenlerini test etmek için yardımcı sınıf
 */
class TestHelper {
    companion object {
        private const val TAG = "TestHelper"

        /**
         * Animasyon dosyalarının varlığını kontrol eder
         */
        fun checkAnimationFiles(context: Context): Boolean {
            val animationFiles = listOf(
                "raw/splash_animation.json",
                "raw/forest_animation.json",
                "raw/ocean_animation.json",
                "raw/desert_animation.json",
                "raw/arctic_animation.json"
            )

            var allFilesExist = true

            for (file in animationFiles) {
                try {
                    context.assets.open(file).close() // Dosya varlığını kontrol et
                } catch (e: Exception) {
                    Log.e(TAG, "Animasyon dosyası bulunamadı: $file - ${e.message}")
                    allFilesExist = false
                }
            }
            return allFilesExist
        }

        /**
         * Uygulama modellerinin doğru çalıştığını test eder
         */
        fun testModels(): Boolean {
            try {
                // Düzeltilmiş Ekosistem modeli
                val ecosystem = com.dogakasifleri.models.Ecosystem(
                    id = 1,
                    name = "Orman",
                    description = "Ağaçlar ve bitkilerle dolu yaşam alanı",
                    imageResId = 1
                )

                // Düzeltilmiş Tür modeli
                val species = com.dogakasifleri.models.Species(
                    id = 1,
                    name = "Kartal",
                    imageResId = 2,
                    description = "Yırtıcı bir kuş türü"
                )

                // Düzeltilmiş Başarı modeli
                val achievement = com.dogakasifleri.models.Achievement(
                    id = 1,
                    title = "Orman Kaşifi",
                    description = "Ormandaki tüm türleri keşfet",
                    iconResId = 1,
                    maxProgress = 10,
                    progress = 0,
                    completed = false
                ) 

                // Düzeltilmiş Karakter seçeneği modeli
                val characterOption = com.dogakasifleri.models.CharacterOption(
                    id = 1,
                    imageResId = R.drawable.avatar_1.toString(),
                    name = "Kaşif Ali"
                )

                // Düzeltilmiş Menü öğesi modeli
                val menuItem = com.dogakasifleri.models.MenuItem(
                    id = 1,
                    title = "Harita",
                    iconResId = R.drawable.ic_map
                )

                // Düzeltilmiş Soru modeli
                val question = com.dogakasifleri.models.Question(
                    id = 1,
                    text = "Kartallar ne yer?",
                    options = listOf("Ot", "Et", "Meyve", "Sebze"),
                    correctAnswerIndex = 1
                )

                return true
            } catch (e: Exception) {
                Log.e(TAG, "Model testi sırasında hata: ${e.stackTraceToString()}")
                return false
            }
        }

        /**
         * Uygulama adaptörlerinin doğru çalıştığını test eder
         */
        fun testAdapters(context: Context): Boolean {
            try {
                // Düzeltilmiş Ekosistem listesi
                val ecosystems = listOf(
                    com.dogakasifleri.models.Ecosystem(
                        id = 1,
                        name = "Orman",
                        description = "Ağaçlar ve bitkilerle dolu yaşam alanı",
                        imageResId = 1
                    )
                )

                val ecosystemAdapter = com.dogakasifleri.adapters.EcosystemAdapter(ecosystems) {}

                // Düzeltilmiş Tür listesi
                val species = listOf(
                    com.dogakasifleri.models.Species(
                     id = 1,
                        name = "Kartal",
                        imageResId = 2,
                        description = "Yırtıcı bir kuş türü"
                    )
                )

                val speciesAdapter = com.dogakasifleri.adapters.SpeciesAdapter(species) {}

                return true
            } catch (e: Exception) {
                Log.e(TAG, "Adaptör testi sırasında hata: ${e.stackTraceToString()}")
                return false
            }
        }
    }
}