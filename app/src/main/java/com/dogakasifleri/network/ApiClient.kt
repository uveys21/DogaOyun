package com.dogakasifleri.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * ApiClient - API isteklerini yöneten sınıf
 * Bu sınıf, sunucu ile iletişim kurar ve veri alışverişi yapar.
 */
class ApiClient {

    companion object {
        private const val TAG = "ApiClient"
        private const val BASE_URL = "https://api.dogakasifleri.com/v1"
        private const val TIMEOUT = 10000 // 10 saniye
    }

    /**
     * GET isteği gönderir
     */
    private suspend fun get(endpoint: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL("$BASE_URL/$endpoint")
            val connection = url.openConnection() as HttpURLConnection
            
            try {
                connection.requestMethod = "GET"
                connection.connectTimeout = TIMEOUT
                connection.readTimeout = TIMEOUT
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    
                    reader.close()
                    response.toString()
                } else {
                    throw Exception("HTTP error code: $responseCode")
                }
            } finally {
                connection.disconnect()
            }
        }
    }

    /**
     * POST isteği gönderir
     */
    private suspend fun post(endpoint: String, data: JSONObject): String {
        return withContext(Dispatchers.IO) {
            val url = URL("$BASE_URL/$endpoint")
            val connection = url.openConnection() as HttpURLConnection
            
            try {
                connection.requestMethod = "POST"
                connection.connectTimeout = TIMEOUT
                connection.readTimeout = TIMEOUT
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty("Accept", "application/json")
                
                val writer = OutputStreamWriter(connection.outputStream)
                writer.write(data.toString())
                writer.flush()
                writer.close()
                
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    
                    reader.close()
                    response.toString()
                } else {
                    throw Exception("HTTP error code: $responseCode")
                }
            } finally {
                connection.disconnect()
            }
        }
    }

    /**
     * Türleri getirir
     */
    suspend fun getSpecies(): List<Map<String, Any>> {
        try {
            val response = get("species")
            val jsonArray = JSONArray(response)
            val speciesList = mutableListOf<Map<String, Any>>()
            
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val speciesMap = mutableMapOf<String, Any>()
                
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    speciesMap[key] = jsonObject.get(key)
                }
                
                speciesList.add(speciesMap)
            }
            
            return speciesList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting species: ${e.message}")
            return emptyList()
        }
    }

    /**
     * Ekosistemleri getirir
     */
    suspend fun getEcosystems(): List<Map<String, Any>> {
        try {
            val response = get("ecosystems")
            val jsonArray = JSONArray(response)
            val ecosystemsList = mutableListOf<Map<String, Any>>()
            
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val ecosystemMap = mutableMapOf<String, Any>()
                
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    ecosystemMap[key] = jsonObject.get(key)
                }
                
                ecosystemsList.add(ecosystemMap)
            }
            
            return ecosystemsList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting ecosystems: ${e.message}")
            return emptyList()
        }
    }

    /**
     * Görevleri getirir
     */
    suspend fun getQuests(): List<Map<String, Any>> {
        try {
            val response = get("quests")
            val jsonArray = JSONArray(response)
            val questsList = mutableListOf<Map<String, Any>>()
            
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val questMap = mutableMapOf<String, Any>()
                
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    questMap[key] = jsonObject.get(key)
                }
                
                questsList.add(questMap)
            }
            
            return questsList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting quests: ${e.message}")
            return emptyList()
        }
    }

    /**
     * Kullanıcı verilerini getirir
     */
    suspend fun getUserData(userId: String): Map<String, Any> {
        try {
            val response = get("users/$userId")
            val jsonObject = JSONObject(response)
            val userDataMap = mutableMapOf<String, Any>()
            
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                userDataMap[key] = jsonObject.get(key)
            }
            
            return userDataMap
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data: ${e.message}")
            return emptyMap()
        }
    }

    /**
     * Kullanıcı verilerini yükler
     */
    suspend fun uploadUserData(userId: String, data: Map<String, Any>): Boolean {
        try {
            val jsonObject = JSONObject()
            for ((key, value) in data) {
                jsonObject.put(key, value)
            }
            
            post("users/$userId", jsonObject)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading user data: ${e.message}")
            return false
        }
    }

    /**
     * Başarıları yükler
     */
    suspend fun uploadAchievements(userId: String, achievements: List<Int>): Boolean {
        try {
            val jsonObject = JSONObject()
            val jsonArray = JSONArray()
            
            for (achievementId in achievements) {
                jsonArray.put(achievementId)
            }
            
            jsonObject.put("achievements", jsonArray)
            
            post("users/$userId/achievements", jsonObject)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading achievements: ${e.message}")
            return false
        }
    }

    /**
     * Güncellemeleri kontrol eder
     */
    suspend fun checkForUpdates(): Pair<Boolean, String> {
        try {
            val response = get("updates/latest")
            val jsonObject = JSONObject(response)
            
            val hasUpdate = jsonObject.getBoolean("has_update")
            val updateMessage = jsonObject.getString("message")
            
            return Pair(hasUpdate, updateMessage)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking for updates: ${e.message}")
            return Pair(false, "Güncelleme kontrolü sırasında hata oluştu")
        }
    }

    /**
     * Hata raporu gönderir
     */
    suspend fun sendErrorReport(userId: String, errorData: Map<String, Any>): Boolean {
        try {
            val jsonObject = JSONObject()
            for ((key, value) in errorData) {
                jsonObject.put(key, value)
            }
            
            jsonObject.put("user_id", userId)
            
            post("errors/report", jsonObject)
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error sending error report: ${e.message}")
            return false
        }
    }
}
