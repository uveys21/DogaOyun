package com.dogakasifleri.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * DatabaseHelper - Uygulama veritabanını yöneten yardımcı sınıf
 * Bu sınıf, SQLite veritabanı işlemlerini gerçekleştirir ve veri tablolarını yönetir.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dogakasifleri.db"
        private const val DATABASE_VERSION = 1

        // Tablo isimleri
        private const val TABLE_USERS = "users"
        private const val TABLE_CHARACTERS = "characters"
        private const val TABLE_SPECIES = "species"
        private const val TABLE_COLLECTION = "collection"
        private const val TABLE_QUESTS = "quests"
        private const val TABLE_ACHIEVEMENTS = "achievements"
        private const val TABLE_ECOSYSTEMS = "ecosystems"
        private const val TABLE_MINI_GAMES = "mini_games"
        
        // Ortak sütunlar
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_URL = "image_url"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_UPDATED_AT = "updated_at"
        
        // Users tablosu sütunları
        private const val COLUMN_USER_AGE = "age"
        private const val COLUMN_USER_AVATAR_ID = "avatar_id"
        private const val COLUMN_USER_POINTS = "points"
        private const val COLUMN_USER_LEVEL = "level"
        private const val COLUMN_USER_LAST_LOGIN = "last_login"
        private const val COLUMN_USER_TOTAL_PLAY_TIME = "total_play_time"
        private const val COLUMN_USER_PARENTAL_CONTROL = "parental_control"
        
        // Characters tablosu sütunları
        private const val COLUMN_CHARACTER_USER_ID = "user_id"
        private const val COLUMN_CHARACTER_AVATAR_ID = "avatar_id"
        private const val COLUMN_CHARACTER_SKIN_TONE = "skin_tone"
        private const val COLUMN_CHARACTER_HAIR_COLOR = "hair_color"
        private const val COLUMN_CHARACTER_HAIR_STYLE = "hair_style"
        private const val COLUMN_CHARACTER_EYE_COLOR = "eye_color"
        private const val COLUMN_CHARACTER_CLOTHES_COLOR = "clothes_color"
        private const val COLUMN_CHARACTER_ACCESSORY_ID = "accessory_id"
        private const val COLUMN_CHARACTER_SPECIAL_ABILITY = "special_ability"
        
        // Species tablosu sütunları
        private const val COLUMN_SPECIES_SCIENTIFIC_NAME = "scientific_name"
        private const val COLUMN_SPECIES_ECOSYSTEM_TYPE = "ecosystem_type"
        private const val COLUMN_SPECIES_TYPE = "type"
        private const val COLUMN_SPECIES_RARITY = "rarity"
        
        // Collection tablosu sütunları
        private const val COLUMN_COLLECTION_USER_ID = "user_id"
        private const val COLUMN_COLLECTION_SPECIES_ID = "species_id"
        private const val COLUMN_COLLECTION_DISCOVERY_DATE = "discovery_date"
        private const val COLUMN_COLLECTION_IS_FAVORITE = "is_favorite"
        private const val COLUMN_COLLECTION_NOTES = "notes"
        
        // Quests tablosu sütunları
        private const val COLUMN_QUEST_ECOSYSTEM_TYPE = "ecosystem_type"
        private const val COLUMN_QUEST_DIFFICULTY = "difficulty"
        private const val COLUMN_QUEST_POINTS = "points"
        private const val COLUMN_QUEST_IS_COMPLETED = "is_completed"
        private const val COLUMN_QUEST_COMPLETION_DATE = "completion_date"
        
        // Achievements tablosu sütunları
        private const val COLUMN_ACHIEVEMENT_USER_ID = "user_id"
        private const val COLUMN_ACHIEVEMENT_TYPE = "type"
        private const val COLUMN_ACHIEVEMENT_POINTS = "points"
        private const val COLUMN_ACHIEVEMENT_IS_EARNED = "is_earned"
        private const val COLUMN_ACHIEVEMENT_EARNED_DATE = "earned_date"
        
        // Ecosystems tablosu sütunları
        private const val COLUMN_ECOSYSTEM_TYPE = "type"
        private const val COLUMN_ECOSYSTEM_LOCATION = "location"
        
        // Mini Games tablosu sütunları
        private const val COLUMN_MINI_GAME_TYPE = "type"
        private const val COLUMN_MINI_GAME_DIFFICULTY = "difficulty"
        private const val COLUMN_MINI_GAME_POINTS = "points"
        private const val COLUMN_MINI_GAME_IS_COMPLETED = "is_completed"
        private const val COLUMN_MINI_GAME_HIGH_SCORE = "high_score"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Users tablosunu oluştur
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID TEXT PRIMARY KEY,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_USER_AGE INTEGER,
                $COLUMN_USER_AVATAR_ID INTEGER,
                $COLUMN_USER_POINTS INTEGER DEFAULT 0,
                $COLUMN_USER_LEVEL INTEGER DEFAULT 1,
                $COLUMN_USER_LAST_LOGIN INTEGER,
                $COLUMN_USER_TOTAL_PLAY_TIME INTEGER DEFAULT 0,
                $COLUMN_USER_PARENTAL_CONTROL INTEGER DEFAULT 0,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createUsersTable)
        
        // Characters tablosunu oluştur
        val createCharactersTable = """
            CREATE TABLE $TABLE_CHARACTERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_CHARACTER_USER_ID TEXT NOT NULL,
                $COLUMN_CHARACTER_AVATAR_ID INTEGER,
                $COLUMN_CHARACTER_SKIN_TONE TEXT,
                $COLUMN_CHARACTER_HAIR_COLOR TEXT,
                $COLUMN_CHARACTER_HAIR_STYLE TEXT,
                $COLUMN_CHARACTER_EYE_COLOR TEXT,
                $COLUMN_CHARACTER_CLOTHES_COLOR TEXT,
                $COLUMN_CHARACTER_ACCESSORY_ID INTEGER,
                $COLUMN_CHARACTER_SPECIAL_ABILITY TEXT,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER,
                FOREIGN KEY($COLUMN_CHARACTER_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createCharactersTable)
        
        // Species tablosunu oluştur
        val createSpeciesTable = """
            CREATE TABLE $TABLE_SPECIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_SPECIES_SCIENTIFIC_NAME TEXT,
                $COLUMN_SPECIES_ECOSYSTEM_TYPE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_SPECIES_TYPE TEXT,
                $COLUMN_SPECIES_RARITY TEXT,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createSpeciesTable)
        
        // Collection tablosunu oluştur
        val createCollectionTable = """
            CREATE TABLE $TABLE_COLLECTION (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_COLLECTION_USER_ID TEXT NOT NULL,
                $COLUMN_COLLECTION_SPECIES_ID INTEGER NOT NULL,
                $COLUMN_COLLECTION_DISCOVERY_DATE INTEGER,
                $COLUMN_COLLECTION_IS_FAVORITE INTEGER DEFAULT 0,
                $COLUMN_COLLECTION_NOTES TEXT,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER,
                FOREIGN KEY($COLUMN_COLLECTION_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID),
                FOREIGN KEY($COLUMN_COLLECTION_SPECIES_ID) REFERENCES $TABLE_SPECIES($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createCollectionTable)
        
        // Quests tablosunu oluştur
        val createQuestsTable = """
            CREATE TABLE $TABLE_QUESTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_QUEST_ECOSYSTEM_TYPE TEXT,
                $COLUMN_QUEST_DIFFICULTY TEXT,
                $COLUMN_QUEST_POINTS INTEGER DEFAULT 0,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_QUEST_IS_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_QUEST_COMPLETION_DATE INTEGER,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createQuestsTable)
        
        // Achievements tablosunu oluştur
        val createAchievementsTable = """
            CREATE TABLE $TABLE_ACHIEVEMENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_ACHIEVEMENT_USER_ID TEXT NOT NULL,
                $COLUMN_ACHIEVEMENT_TYPE TEXT,
                $COLUMN_ACHIEVEMENT_POINTS INTEGER DEFAULT 0,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_ACHIEVEMENT_IS_EARNED INTEGER DEFAULT 0,
                $COLUMN_ACHIEVEMENT_EARNED_DATE INTEGER,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER,
                FOREIGN KEY($COLUMN_ACHIEVEMENT_USER_ID) REFERENCES $TABLE_USERS($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createAchievementsTable)
        
        // Ecosystems tablosunu oluştur
        val createEcosystemsTable = """
            CREATE TABLE $TABLE_ECOSYSTEMS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_ECOSYSTEM_TYPE TEXT,
                $COLUMN_ECOSYSTEM_LOCATION TEXT,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createEcosystemsTable)
        
        // Mini Games tablosunu oluştur
        val createMiniGamesTable = """
            CREATE TABLE $TABLE_MINI_GAMES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_MINI_GAME_TYPE TEXT,
                $COLUMN_MINI_GAME_DIFFICULTY TEXT,
                $COLUMN_MINI_GAME_POINTS INTEGER DEFAULT 0,
                $COLUMN_IMAGE_URL TEXT,
                $COLUMN_MINI_GAME_IS_COMPLETED INTEGER DEFAULT 0,
                $COLUMN_MINI_GAME_HIGH_SCORE INTEGER DEFAULT 0,
                $COLUMN_CREATED_AT INTEGER,
                $COLUMN_UPDATED_AT INTEGER
            )
        """.trimIndent()
        db.execSQL(createMiniGamesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Veritabanı sürümü yükseltildiğinde tabloları sil ve yeniden oluştur
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MINI_GAMES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ECOSYSTEMS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ACHIEVEMENTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COLLECTION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SPECIES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHARACTERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }
    
    /**
     * Veritabanına örnek veriler ekler
     */
    fun insertSampleData() {
        val db = writableDatabase
        
        // Örnek ekosistemler
        val ecosystems = arrayOf(
            arrayOf("Amazon Yağmur Ormanı", "Dünyanın en büyük yağmur ormanı", "Orman", "-3.4653,-62.2159", "forest.png"),
            arrayOf("Büyük Mercan Resifi", "Dünyanın en büyük mercan resifi", "Okyanus", "-18.2871,147.6992", "ocean.png"),
            arrayOf("Sahra Çölü", "Dünyanın en büyük sıcak çölü", "Çöl", "23.4162,25.6628", "desert.png"),
            arrayOf("Antarktika", "Dünyanın en soğuk kıtası", "Kutup", "-79.4063,0.3149", "arctic.png")
        )
        
        for (ecosystem in ecosystems) {
            val values = android.content.ContentValues().apply {
                put(COLUMN_NAME, ecosystem[0])
                put(COLUMN_DESCRIPTION, ecosystem[1])
                put(COLUMN_ECOSYSTEM_TYPE, ecosystem[2])
                put(COLUMN_ECOSYSTEM_LOCATION, ecosystem[3])
                put(COLUMN_IMAGE_URL, ecosystem[4])
                put(COLUMN_CREATED_AT, System.currentTimeMillis())
                put(COLUMN_UPDATED_AT, System.currentTimeMillis())
            }
            db.insert(TABLE_ECOSYSTEMS, null, values)
        }
        
        // Örnek türler
        val species = arrayOf(
            arrayOf("Jaguar", "Panthera onca", "Orman", "Yağmur ormanlarının en güçlü avcısı", "jaguar.png", "Animal", "Rare"),
            arrayOf("Anakonda", "Eunectes murinus", "Orman", "Dünyanın en büyük yılanlarından biri", "anaconda.png", "Animal", "Uncommon"),
            arrayOf("Orkinos", "Thunnus thynnus", "Okyanus", "Hızlı yüzen büyük bir balık türü", "tuna.png", "Animal", "Common"),
            arrayOf("Mercan", "Anthozoa", "Okyanus", "Deniz ekosisteminin yapı taşları", "coral.png", "Plant", "Uncommon"),
            arrayOf("Deve", "Camelus dromedarius", "Çöl", "Çöl koşullarına uyum sağlamış memeli", "camel.png", "Animal", "Common"),
            arrayOf("Kaktüs", "Cactaceae", "Çöl", "Su depolayabilen çöl bitkisi", "cactus.png", "Plant", "Common"),
            arrayOf("Kutup Ayısı", "Ursus maritimus", "Kutup", "Kuzey kutbunun en büyük avcısı", "polar_bear.png", "Animal", "Rare"),
            arrayOf("Penguen", "Spheniscidae", "Kutup", "Antarktika'nın simge hayvanı", "penguin.png", "Animal", "Uncommon")
        )
        
        for (specie in species) {
            val values = android.content.ContentValues().apply {
                put(COLUMN_NAME, specie[0])
                put(COLUMN_SPECIES_SCIENTIFIC_NAME, specie[1])
                put(COLUMN_SPECIES_ECOSYSTEM_TYPE, specie[2])
                put(COLUMN_DESCRIPTION, specie[3])
                put(COLUMN_IMAGE_URL, specie[4])
                put(COLUMN_SPECIES_TYPE, specie[5])
                put(COLUMN_SPECIES_RARITY, specie[6])
                put(COLUMN_CREATED_AT, System.currentTimeMillis())
                put(COLUMN_UPDATED_AT, System.currentTimeMillis())
            }
            db.insert(TABLE_SPECIES, null, values)
        }
        
        db.close()
    }
}
