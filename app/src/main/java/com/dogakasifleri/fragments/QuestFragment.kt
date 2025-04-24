package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.models.Quest
import com.dogakasifleri.utils.AnimationUtils
import com.dogakasifleri.viewmodels.QuestViewModel
import com.dogakasifleri.game.quests.QuestManager

/**
 * QuestFragment - Kullanıcının tamamlayabileceği görevleri gösteren fragment
 * Bu fragment, çocukların doğa hakkında bilgi edinmesini sağlayan çeşitli görevleri listeler
 * ve bu görevlerin tamamlanma durumunu takip eder.
 */
class QuestFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestAdapter
    private lateinit var viewModel: QuestViewModel
    private lateinit var questManager: QuestManager
    private var quests: List<Quest> = listOf()
    private var ecosystemType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quest, container, false)
        
        // Ekosistem tipini al
        ecosystemType = arguments?.getString("ECOSYSTEM_TYPE", "") ?: ""
        
        // ViewModel'i başlat
        viewModel = ViewModelProvider(this).get(QuestViewModel::class.java)
        
        // Görev yöneticisini başlat
        questManager = QuestManager(requireContext())
        
        // RecyclerView ayarları
        recyclerView = view.findViewById(R.id.recyclerViewQuests)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = QuestAdapter(requireContext(), quests) { quest ->
            startQuest(quest)
        }
        recyclerView.adapter = adapter
        
        // Görevleri yükle
        loadQuests()
        
        // Animasyonları uygula
        AnimationUtils.applyItemAnimations(recyclerView)
        
        // Ekosistem başlığını ayarla
        view.findViewById<TextView>(R.id.textViewQuestTitle).text = 
            if (ecosystemType.isNotEmpty()) "$ecosystemType Görevleri" else "Tüm Görevler"
        
        return view
    }
    
    /**
     * Görevleri yükler
     */
    private fun loadQuests() {
        if (ecosystemType.isNotEmpty()) {
            // Belirli ekosistem için görevleri al
            viewModel.getQuestsForEcosystem(ecosystemType).observe(viewLifecycleOwner, { result ->
                quests = result
                updateUI()
            })
        } else {
            // Tüm görevleri al
            viewModel.getAllQuests().observe(viewLifecycleOwner, { result ->
                quests = result
                updateUI()
            })
        }
    }
    
    /**
     * UI'ı günceller
     */
    private fun updateUI() {
        // Adaptörü güncelle
        adapter.updateQuests(quests)
        
        // Boş durum kontrolü
        checkEmptyState()
        
        // İlerleme durumunu güncelle
        updateProgressStatus()
    }
    
    /**
     * Boş durum kontrolü yapar
     */
    private fun checkEmptyState() {
        if (quests.isEmpty()) {
            view?.findViewById<View>(R.id.emptyQuestsView)?.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            view?.findViewById<View>(R.id.emptyQuestsView)?.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    /**
     * İlerleme durumunu günceller
     */
    private fun updateProgressStatus() {
        val totalQuests = quests.size
        val completedQuests = quests.count { it.isCompleted }
        
        // İlerleme metnini güncelle
        view?.findViewById<TextView>(R.id.textViewQuestProgress)?.text = 
            "$completedQuests / $totalQuests görev tamamlandı"
        
        // İlerleme çubuğunu güncelle
        val progressBar = view?.findViewById<ProgressBar>(R.id.progressBarQuests)
        progressBar?.max = totalQuests
        progressBar?.progress = completedQuests
    }
    
    /**
     * Görevi başlatır
     */
    private fun startQuest(quest: Quest) {
        // Görev detay sayfasına git
        val intent = Intent(activity, QuestActivity::class.java).apply {
            putExtra("QUEST_ID", quest.id)
        }
        startActivity(intent)
        
        // Geçiş animasyonu
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    
    companion object {
        fun newInstance(ecosystemType: String): QuestFragment {
            val fragment = QuestFragment()
            val args = Bundle()
            args.putString("ECOSYSTEM_TYPE", ecosystemType)
            fragment.arguments = args
            return fragment
        }
    }
}
