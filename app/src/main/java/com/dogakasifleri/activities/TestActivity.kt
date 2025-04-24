package com.dogakasifleri.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.utils.TestHelper

/**
 * Test aktivitesi - Uygulama bileşenlerini test etmek için kullanılır
 * Not: Bu aktivite sadece geliştirme aşamasında kullanılır, son kullanıcıya sunulmaz
 */
class TestActivity : AppCompatActivity() {
    
    private val TAG = "TestActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Tüm testleri çalıştır
        runAllTests()
    }
    
    /**
     * Tüm test senaryolarını çalıştırır
     */
    private fun runAllTests() {
        Log.d(TAG, "Testler başlatılıyor...")
        
        var allTestsPassed = true
        
        // Animasyon dosyalarını kontrol et
        val animationFilesExist = TestHelper.checkAnimationFiles(this)
        logTestResult("Animasyon dosyaları testi", animationFilesExist)
        allTestsPassed = allTestsPassed && animationFilesExist
        
        // Model testlerini çalıştır
        val modelsTestPassed = TestHelper.testModels()
        logTestResult("Model testi", modelsTestPassed)
        allTestsPassed = allTestsPassed && modelsTestPassed
        
        // Adaptör testlerini çalıştır
        val adaptersTestPassed = TestHelper.testAdapters(this)
        logTestResult("Adaptör testi", adaptersTestPassed)
        allTestsPassed = allTestsPassed && adaptersTestPassed
        
        // Sonuçları göster
        if (allTestsPassed) {
            Log.d(TAG, "Tüm testler başarıyla tamamlandı!")
            Toast.makeText(this, "Tüm testler başarıyla tamamlandı!", Toast.LENGTH_LONG).show()
        } else {
            Log.e(TAG, "Bazı testler başarısız oldu!")
            Toast.makeText(this, "Bazı testler başarısız oldu! Logları kontrol edin.", Toast.LENGTH_LONG).show()
        }
    }
    
    /**
     * Test sonucunu loglar
     * 
     * @param testName Test adı
     * @param passed Test başarılı mı
     */
    private fun logTestResult(testName: String, passed: Boolean) {
        if (passed) {
            Log.d(TAG, "$testName: BAŞARILI")
        } else {
            Log.e(TAG, "$testName: BAŞARISIZ")
        }
    }
}
