package com.dogakasifleri.repositories

import com.dogakasifleri.models.Ecosystem

class EcosystemRepositoryImpl : EcosystemRepository {
    override fun getEcosystemById(ecosystemId: Int): Ecosystem? {
        return when (ecosystemId) {
            1 -> Ecosystem(
                1,
                "Orman",
                "Yeşil ve huzurlu orman ekosistemi.",
                "Orman"
            )
            2 -> Ecosystem(
                2,
                "Okyanus",
                "Gizemli ve derin okyanus ekosistemi.",
                "Okyanus"
            )
            3 -> Ecosystem(
                3,
                "Çöl",
                "Sıcak ve kurak çöl ekosistemi.",
                "Çöl"
            )
            4 -> Ecosystem(
                4,
                "Kutup",
                "Soğuk ve buzlu kutup ekosistemi.",
                "Kutup"
            )
            else -> null
        }
    }
}