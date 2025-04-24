package com.dogakasifleri.repositories

import com.dogakasifleri.R
import com.dogakasifleri.models.Species

class SpeciesRepositoryImpl : SpeciesRepository {
    override fun getSpeciesByEcosystemId(ecosystemId: Int): List<Species> {
        return when (ecosystemId) {
            1 -> getForestSpecies()
            2 -> getOceanSpecies()
            3 -> getDesertSpecies()
            4 -> getArcticSpecies()
            else -> emptyList()
        }
    }

    private fun getForestSpecies(): List<Species> {
        return listOf(
            Species(
                id = 1,
                name = "Meşe Ağacı",
                scientificName = "Quercus robur",
                type = "Orman",
                shortDescription = "Dayanıklı ve uzun ömürlü ağaç",
                imageResId = R.drawable.species_oak_tree,
                description = "Meşe ağaçları, güçlü ve uzun ömürlü ağaçlardır. Palamut adı verilen meyveler üretirler.",
                facts = "Meşe ağaçları 1000 yıla kadar yaşayabilir.",
                level = 1,
                experience = 0
            ),
            Species(
                id = 2,
                name = "Sincap",
                scientificName = "Sciurus vulgaris",
                type = "Orman",
                shortDescription = "Çevik kemirgen",
                imageResId = R.drawable.species_squirrel,
                description = "Sincaplar, ağaçlarda yaşayan ve fındık, tohum gibi besinlerle beslenen kemirgenlerdir.",
                facts = "Sincaplar koku alma duyularını kullanarak kış için sakladıkları yiyecekleri bulabilirler.",
                level = 1,
                experience = 0
            ),
            Species(
                id = 3,
                name = "Geyik",
                scientificName = "Cervus elaphus",
                type = "Orman",
                shortDescription = "Asil ve zarif otçul",
                imageResId = R.drawable.species_deer,
                description = "Geyikler, ormanlarda yaşayan otçul hayvanlardır. Erkek geyiklerin boynuzları vardır.",
                facts = "Erkek geyiklerin boynuzları her yıl düşer ve yeniden büyür.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 4,
                name = "Mantar",
                scientificName = "Fungorum varia",
                type = "Orman",
                shortDescription = "Gizemli yaşam formu",
                imageResId = R.drawable.species_mushroom,
                description = "Mantarlar, ormanlarda yetişen ve çürükçül beslenen organizmalardır.",
                facts = "Mantarlar ne bitki ne de hayvandır, ayrı bir alemde sınıflandırılırlar.",
                level = 1,
                experience = 0
            )
        )
    }

    private fun getOceanSpecies(): List<Species> {
        return listOf(
            Species(
                id = 5,
                name = "Yunus",
                scientificName = "Delphinus delphis",
                type = "Okyanus",
                shortDescription = "Zeki deniz memelisi",
                imageResId = R.drawable.species_dolphin,
                description = "Yunuslar, zeki deniz memelileridir. Gruplar halinde yaşar ve ekolokatör kullanarak avlanırlar.",
                facts = "Yunuslar uyurken beyinlerinin sadece yarısını kapatırlar.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 6,
                name = "Mercan",
                scientificName = "Anthozoa varia",
                type = "Okyanus",
                shortDescription = "Renkli deniz canlıları",
                imageResId = R.drawable.species_coral,
                description = "Mercanlar, sıcak ve sığ sularda yaşayan koloniler halindeki deniz canlılarıdır.",
                facts = "Mercanlar aslında hayvandır, bitki değildir.",
                level = 1,
                experience = 0
            ),
            Species(
                id = 7,
                name = "Deniz Yıldızı",
                scientificName = "Asteroidea varia",
                type = "Okyanus",
                shortDescription = "Beş kollu deniz canlısı",
                imageResId = R.drawable.species_starfish,
                description = "Deniz yıldızları, beş kollu deniz canlılarıdır. Kopan uzuvlarını yenileyebilirler.",
                facts = "Deniz yıldızları mideleriyle yer, ağızları yoktur.",
                level = 1,
                experience = 0
            ),
            Species(
                id = 8,
                name = "Ahtapot",
                scientificName = "Octopus vulgaris",
                type = "Okyanus",
                shortDescription = "Sekiz kollu zeki canlı",
                imageResId = R.drawable.species_octopus,
                description = "Ahtapotlar, sekiz kollu ve çok zeki deniz canlılarıdır.",
                facts = "Ahtapotların 3 kalbi ve mavi kanı vardır.",
                level = 3,
                experience = 20
            )
        )
    }

    private fun getDesertSpecies(): List<Species> {
        return listOf(
            Species(
                id = 9,
                name = "Kaktüs",
                scientificName = "Cactaceae varia",
                type = "Çöl",
                shortDescription = "Çöl koşullarına uyum sağlamış bitki",
                imageResId = R.drawable.species_cactus,
                description = "Kaktüsler, çöl ortamına uyum sağlamış, su depolayabilen bitkilerdir.",
                facts = "Kaktüsler 200 yıla kadar yaşayabilir.",
                level = 1,
                experience = 0
            ),
            Species(
                id = 10,
                name = "Çöl Tilkisi",
                scientificName = "Vulpes zerda",
                type = "Çöl",
                shortDescription = "Geniş kulaklı çöl canlısı",
                imageResId = R.drawable.species_desert_fox,
                description = "Çöl tilkileri, büyük kulaklara sahip ve geceleri aktif olan hayvanlardır.",
                facts = "Çöl tilkilerinin büyük kulakları vücut ısılarını düzenlemelerine yardımcı olur.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 11,
                name = "Akrep",
                scientificName = "Scorpiones varia",
                type = "Çöl",
                shortDescription = "Zehirli eklem bacaklı",
                imageResId = R.drawable.species_scorpion,
                description = "Akrepler, zehirli iğneleri olan eklem bacaklılardır.",
                facts = "Akrepler ultraviyole ışık altında parlarlar.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 12,
                name = "Deve",
                scientificName = "Camelus dromedarius",
                type = "Çöl",
                shortDescription = "Çölün yük hayvanı",
                imageResId = R.drawable.species_camel,
                description = "Develer, hörgüçlerinde yağ depolayarak uzun süre susuz kalabilen hayvanlardır.",
                facts = "Develer hörgüçlerinde yağ depolar, su değil.",
                level = 3,
                experience = 20
            )
        )
    }

    private fun getArcticSpecies(): List<Species> {
        return listOf(
            Species(
                id = 13,
                name = "Kutup Ayısı",
                scientificName = "Ursus maritimus",
                type = "Kutup",
                shortDescription = "Büyük kutup memelisi",
                imageResId = R.drawable.species_polar_bear,
                description = "Kutup ayıları, kalın kürkleri sayesinde soğuk iklimlerde yaşayabilen büyük memelilerdir.",
                facts = "Kutup ayılarının derisi siyahtır, kürkleri ise şeffaftır.",
                level = 3,
                experience = 20
            ),
            Species(
                id = 14,
                name = "Penguen",
                scientificName = "Spheniscidae varia",
                type = "Kutup",
                shortDescription = "Uçamayan kuş",
                imageResId = R.drawable.species_penguin,
                description = "Penguenler, uçamayan ama çok iyi yüzen kuşlardır.",
                facts = "Penguenler tek eşlidir ve aynı eşle hayatları boyunca birlikte kalırlar.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 15,
                name = "Fok",
                scientificName = "Phocidae varia",
                type = "Kutup",
                shortDescription = "Hem karada hem suda yaşayabilen memeli",
                imageResId = R.drawable.species_seal,
                description = "Foklar, hem karada hem de suda yaşayabilen deniz memelileridir.",
                facts = "Foklar 20 dakika boyunca nefeslerini tutabilirler.",
                level = 2,
                experience = 10
            ),
            Species(
                id = 16,
                name = "Ren Geyiği",
                scientificName = "Rangifer tarandus",
                type = "Kutup",
                shortDescription = "Kutup bölgelerinde yaşayan geyik",
                imageResId = R.drawable.species_reindeer,
                description = "Ren geyikleri, kutup bölgelerinde yaşayan ve hem erkekleri hem de dişileri boynuz taşıyan geyiklerdir.",
                facts = "Ren geyiklerinin gözleri yazın altın renginden kışın maviye döner.",
                level = 3,
                experience = 20
            )
        )
    }
}