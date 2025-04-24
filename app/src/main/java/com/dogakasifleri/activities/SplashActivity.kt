package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.R

/**
 * Splash ekranı - Uygulama başlangıcında gösterilir
 */
class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3000L // 3 saniye

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Logo animasyonu
        val logoImageView = findViewById<ImageView>(R.id.ivLogo)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logoImageView.startAnimation(fadeInAnimation)

        // Splash ekranından sonra ana ekrana geçiş
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}
