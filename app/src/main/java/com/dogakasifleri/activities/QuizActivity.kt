package com.dogakasifleri.activities

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dogakasifleri.R
import com.dogakasifleri.models.Question
import com.dogakasifleri.utils.AchievementManager

class QuizActivity : AppCompatActivity() {

    // UI Components
    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var rgAnswers: RadioGroup
    private lateinit var rbAnswer1: RadioButton
    private lateinit var rbAnswer2: RadioButton
    private lateinit var rbAnswer3: RadioButton
    private lateinit var btnSubmit: Button
    private lateinit var btnBack: Button

    // Quiz Variables
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Toolbar Setup
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize UI Components
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber)
        tvQuestion = findViewById(R.id.tvQuestion)
        rgAnswers = findViewById(R.id.rgAnswers)
        rbAnswer1 = findViewById(R.id.rbAnswer1)
        rbAnswer2 = findViewById(R.id.rbAnswer2)
        rbAnswer3 = findViewById(R.id.rbAnswer3)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnBack = findViewById(R.id.btnBack)

        // Get species ID from intent
        val speciesId = intent.getIntExtra("SPECIES_ID", 0)

        // Load questions and start quiz
        questions = getQuestionsForSpecies(speciesId)
        showQuestion(currentQuestionIndex)

        // Button Click Listeners
        btnSubmit.setOnClickListener { checkAnswer() }
        btnBack.setOnClickListener { finish() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    private fun getQuestionsForSpecies(speciesId: Int): List<Question> {
        // Türe göre soruları döndür
        return when (speciesId) {
            1 -> { // Meşe Ağacı
                listOf(
                    Question("Meşe ağaçları hangi familyaya aittir?", 
                            listOf("Kayıngiller", "Çamgiller", "Kayıngiller"), 0),
                    Question("Meşe ağaçlarının meyvesine ne ad verilir?", 
                            listOf("Palamut", "Kozalak", "Kestane"), 0),
                    Question("Meşe ağaçları ortalama kaç yıl yaşar?", 
                            listOf("100-200 yıl", "200-500 yıl", "500-1000 yıl"), 2)
                )
            }
            2 -> { // Sincap
                listOf(
                    Question("Sincaplar hangi hayvan sınıfına aittir?", 
                            listOf("Kemirgenler", "Etçiller", "Sürüngenler"), 0),
                    Question("Sincaplar kış uykusuna yatar mı?", 
                            listOf("Evet, tüm kış boyunca", "Hayır, kış boyunca aktiftir", "Sadece çok soğuk günlerde"), 1),
                    Question("Sincaplar ne ile beslenir?", 
                            listOf("Fındık ve tohumlar", "Küçük böcekler", "Yapraklar"), 0)
                )
            }
            // Diğer türler için sorular...
            else -> {
                listOf(
                    Question("Bu tür hangi ekosistemde yaşar?", 
                            listOf("Orman", "Okyanus", "Çöl"), 0),
                    Question("Bu türün en belirgin özelliği nedir?", 
                            listOf("Hızlı koşması", "Suda yaşaması", "Ağaçlarda yaşaması"), 2),
                    Question("Bu tür ne ile beslenir?", 
                            listOf("Bitkiler", "Diğer hayvanlar", "Her ikisi de"), 0)
                )
            }
        }
    }

    private fun showQuestion(index: Int) {
        // Soru numarasını göster
        tvQuestionNumber.text = "Soru ${index + 1}/${questions.size}"
        
        // Soruyu göster
        val question = questions[index]
        tvQuestion.text = question.text
        
        // Cevap seçeneklerini göster
        rbAnswer1.text = question.options[0]
        rbAnswer2.text = question.options[1]
        rbAnswer3.text = question.options[2]
        
        // Seçimleri temizle
        rgAnswers.clearCheck()
    }

    private fun checkAnswer() {
        // Seçilen cevabı kontrol et
        val selectedId = rgAnswers.checkedRadioButtonId
        
        if (selectedId == -1) {
            // Cevap seçilmemiş
            AlertDialog.Builder(this)
                .setTitle("Uyarı")
                .setMessage("Lütfen bir cevap seçin.")
                .setPositiveButton("Tamam", null)
                .show()
            return
        }
        
        // Seçilen cevabın indeksini bul
        val selectedIndex = when (selectedId) {
            R.id.rbAnswer1 -> 0
            R.id.rbAnswer2 -> 1
            R.id.rbAnswer3 -> 2
            else -> -1
        }
        
        // Doğru cevap mı kontrol et
        val currentQuestion = questions[currentQuestionIndex]
        if (selectedIndex == currentQuestion.correctAnswerIndex) {
            correctAnswers++
        }
        
        // Sonraki soruya geç veya quiz'i bitir
        currentQuestionIndex++
        
        if (currentQuestionIndex < questions.size) {
            // Sonraki soruyu göster
            showQuestion(currentQuestionIndex)
        } else {
            // Quiz bitti, sonuçları göster
            showResults()
        }
    }

    private fun showResults() {
        // Sonuçları göster
        val score = (correctAnswers.toFloat() / questions.size) * 100
        val message = "Quiz tamamlandı!\n\nDoğru cevaplar: $correctAnswers/${questions.size}\nPuanınız: ${score.toInt()}%"
        
        // Başarı durumuna göre rozet ver        
        if (score >= 80) {
            val achievementManager = AchievementManager(this)
            achievementManager.unlockAchievement("quiz_master")
        }
        
        // Sonuç dialogunu göster
        AlertDialog.Builder(this)
            .setTitle("Quiz Sonuçları")
            .setMessage(message)
            .setPositiveButton("Tamam") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
}
