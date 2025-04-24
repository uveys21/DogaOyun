package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dogakasifleri.R

/**
 * Giriş ekranı - Kullanıcı girişi ve kayıt işlemleri
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var tvForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // UI elemanlarını tanımla
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        // Giriş butonu tıklama olayı
        btnLogin.setOnClickListener {
            // Gerçek uygulamada burada kimlik doğrulama yapılır
            // Şimdilik doğrudan ana ekrana geçiş yapıyoruz
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Kayıt ol butonu tıklama olayı
        btnRegister.setOnClickListener {
            // Kayıt ekranına geçiş
            // Gerçek uygulamada burada kayıt ekranına geçilir
            // Şimdilik doğrudan karakter oluşturma ekranına geçiyoruz
            val intent = Intent(this, CharacterCreationActivity::class.java)
            startActivity(intent)
        }

        // Şifremi unuttum tıklama olayı
        tvForgotPassword.setOnClickListener {
            // Şifre sıfırlama işlemi
            // Bu demo için boş bırakıldı
        }
    }
}
