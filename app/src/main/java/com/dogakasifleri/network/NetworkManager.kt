package com.dogakasifleri.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import com.dogakasifleri.utils.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * NetworkManager - Ağ bağlantılarını yöneten sınıf
 * Bu sınıf, internet bağlantısını kontrol eder ve ağ isteklerini yönetir.
 */
class NetworkManager(private val context: Context) {

    private val preferenceManager = PreferenceManager(context)
    private val apiClient = ApiClient()
    
    companion object {
        private const val TAG = "NetworkManager"
        private const val CONNECTION_TIMEOUT = 5000 // 5 saniye
    }

    /**
     * İnternet bağlantısının olup olmadığını kontrol eder
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Sunucu bağlantısını kontrol eder
     */
    suspend fun isServerReachable(serverUrl: String = "https://api.dogakasifleri.com"): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(serverUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = CONNECTION_TIMEOUT
                connection.readTimeout = CONNECTION_TIMEOUT
                connection.requestMethod = "HEAD"
                val responseCode = connection.responseCode
                responseCode == HttpURLConnection.HTTP_OK
            } catch (e: IOException) {
                Log.e(TAG, "Error checking server connection: ${e.message}")
                false
            }
        }
    }

    /**
     * Veri senkronizasyonunun gerekli olup olmadığını kontrol eder
     */
    fun isSyncRequired(): Boolean {
        val lastSyncTime = preferenceManager.preferences.getLong("last_sync_time", 0)
        val currentTime = System.currentTimeMillis()
        val syncInterval = 24 * 60 * 60 * 1000 // 24 saat
        
        return currentTime - lastSyncTime > syncInterval
    }

    /**
     * Veri senkronizasyonu yapar
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun syncData(): Boolean {
        if (!isNetworkAvailable()) {
            return false
        }
        
        return withContext(Dispatchers.IO) {
            try {
                // Sunucudan verileri al
                val speciesData = apiClient.getSpecies()
                val ecosystemsData = apiClient.getEcosystems()
                val questsData = apiClient.getQuests()
                
                // Verileri yerel veritabanına kaydet
                // Bu kısım gerçek uygulamada DatabaseHelper ile yapılır
                
                // Son senkronizasyon zamanını güncelle
                preferenceManager.editor.putLong("last_sync_time", System.currentTimeMillis())
                preferenceManager.editor.apply()
                
                true
            } catch (e: Exception) {
                Log.e(TAG, "Error syncing data: ${e.message}")
                false
            }
        }
    }

    /**
     * Kullanıcı verilerini sunucuya gönderir
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun uploadUserData(userId: String, data: Map<String, Any>): Boolean {
        if (!isNetworkAvailable()) {
            return false
        }
        
        return withContext(Dispatchers.IO) {
            try {
                apiClient.uploadUserData(userId, data)
                true
            } catch (e: Exception) {
                Log.e(TAG, "Error uploading user data: ${e.message}")
                false
            }
        }
    }

    /**
     * Kullanıcı verilerini sunucudan alır
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun downloadUserData(userId: String): Map<String, Any>? {
        if (!isNetworkAvailable()) {
            return null
        }
        
        return withContext(Dispatchers.IO) {
            try {
                apiClient.getUserData(userId)
            } catch (e: Exception) {
                Log.e(TAG, "Error downloading user data: ${e.message}")
                null
            }
        }
    }

    /**
     * Kullanıcı başarılarını sunucuya gönderir
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun uploadAchievements(userId: String, achievements: List<Int>): Boolean {
        if (!isNetworkAvailable()) {
            return false
        }
        
        return withContext(Dispatchers.IO) {
            try {
                apiClient.uploadAchievements(userId, achievements)
                true
            } catch (e: Exception) {
                Log.e(TAG, "Error uploading achievements: ${e.message}")
                false
            }
        }
    }

    /**
     * Uygulama güncellemelerini kontrol eder
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun checkForUpdates(): Pair<Boolean, String> {
        if (!isNetworkAvailable()) {
            return Pair(false, "İnternet bağlantısı yok")
        }
        
        return withContext(Dispatchers.IO) {
            try {
                val updateInfo = apiClient.checkForUpdates()
                Pair(updateInfo.first, updateInfo.second)
            } catch (e: Exception) {
                Log.e(TAG, "Error checking for updates: ${e.message}")
                Pair(false, "Güncelleme kontrolü sırasında hata oluştu")
            }
        }
    }

    /**
     * Hata raporunu sunucuya gönderir
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun sendErrorReport(userId: String, errorData: Map<String, Any>): Boolean {
        if (!isNetworkAvailable()) {
            return false
        }
        
        return withContext(Dispatchers.IO) {
            try {
                apiClient.sendErrorReport(userId, errorData)
                true
            } catch (e: Exception) {
                Log.e(TAG, "Error sending error report: ${e.message}")
                false
            }
        }
    }
}
