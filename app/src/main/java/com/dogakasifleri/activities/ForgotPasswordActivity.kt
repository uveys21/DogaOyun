package com.dogakasifleri.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dogakasifleri.R

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var btnResetPassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        toolbar = findViewById(R.id.toolbar)
        btnResetPassword = findViewById(R.id.btnResetPassword)

        setupToolbar()
        setupResetPasswordButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.forgot_password_title)
    }

    private fun setupResetPasswordButton() {
        btnResetPassword.setOnClickListener {
            // Şifre sıfırlama işlemi burada yapılacak
            // Örneğin: E-posta gönderimi, yeni şifre oluşturma vb.
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}