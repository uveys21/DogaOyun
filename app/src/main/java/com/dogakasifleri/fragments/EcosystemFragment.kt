package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.adapters.SpeciesAdapter
import com.dogakasifleri.models.Ecosystem
import com.dogakasifleri.models.Species
import com.dogakasifleri.utils.AnimationUtils
import com.dogakasifleri.viewmodels.EcosystemViewModel

/**
 * EcosystemFragment - Belirli bir ekosistemdeki türleri gösteren fragment
 * Bu fragment, seçilen ekosistemdeki bitki ve hayvan türlerini listeler ve
 * kullanıcının bu türlere tıklayarak detaylarına erişmesini sağlar.
 */
class EcosystemFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SpeciesAdapter
    private lateinit var viewModel: EcosystemViewModel
    private var ecosystem: Ecosystem? = null
    private var speciesList: MutableList<Species> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ecosystem, container, false)
        
        // Ekosistem ID'sini al
        val ecosystemId = arguments?.getInt("ECOSYSTEM_ID", -1) ?: -1
        
        // ViewModel'i başlat
        viewModel = ViewModelProvider(this).get(EcosystemViewModel::class.java)
        
        // Ekosistemi yükle
        loadEcosystem(ecosystemId)
        
        // RecyclerView ayarları
        recyclerView = view.findViewById(R.id.recyclerViewSpecies)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = SpeciesAdapter(speciesList, this@EcosystemFragment) { species ->
            showSpeciesDetails(species)
        }
        recyclerView.adapter = adapter
        
        // Animasyonları uygula
        AnimationUtils.applyItemAnimations(recyclerView)
        
        // Ekosistem başlığını ayarla
        view.findViewById<TextView>(R.id.textViewEcosystemTitle).text = ecosystem?.name ?: "Ekosistem"
        
        // Ekosistem açıklamasını ayarla
        view.findViewById<TextView>(R.id.textViewEcosystemDescription).text = ecosystem?.description ?: ""
        
        // Ekosistem animasyonunu yükle
        loadEcosystemAnimation(view, ecosystem?.type ?: "")
        
        return view
    }
    
    /**
     * Ekosistemi yükler
     */
    private fun loadEcosystem(ecosystemId: Int) {
        if (ecosystemId == -1) return
        
        // ViewModel'den ekosistemi al
        viewModel.getEcosystem(ecosystemId).observe(viewLifecycleOwner, { result ->
            ecosystem = result
            
            // UI'ı güncelle
            updateUI()
        })
        
        // ViewModel'den türleri al
        viewModel.getSpeciesForEcosystem(ecosystemId).observe(viewLifecycleOwner, { result ->
            speciesList = result
            
            // Adaptörü güncelle
            adapter.updateSpecies(speciesList)
            
            // Boş durum kontrolü
            checkEmptyState()
        })
    }
    
    /**
     * UI'ı günceller
     */
    private fun updateUI() {
        view?.findViewById<TextView>(R.id.textViewEcosystemTitle)?.text = ecosystem?.name ?: "Ekosistem"
        view?.findViewById<TextView>(R.id.textViewEcosystemDescription)?.text = ecosystem?.description ?: ""
        
        // Ekosistem tipine göre arka plan rengini ayarla
        when (ecosystem?.type) {
            "Orman" -> view?.setBackgroundResource(R.drawable.bg_ecosystem_forest)
            "Okyanus" -> view?.setBackgroundResource(R.drawable.bg_ecosystem_ocean)
            "Çöl" -> view?.setBackgroundResource(R.drawable.bg_ecosystem_desert)
            "Kutup" -> view?.setBackgroundResource(R.drawable.bg_ecosystem_arctic)
        }
    }
    
    /**
     * Boş durum kontrolü yapar
     */
    private fun checkEmptyState() {
        if (speciesList.isEmpty()) {
            view?.findViewById<View>(R.id.emptySpeciesView)?.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            view?.findViewById<View>(R.id.emptySpeciesView)?.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }
    
    /**
     * Ekosistem animasyonunu yükler
     */
    private fun loadEcosystemAnimation(view: View, ecosystemType: String) {
        val animationView = view.findViewById<LottieAnimationView>(R.id.animationViewEcosystem)
        
        // Ekosistem tipine göre animasyonu ayarla
        when (ecosystemType) {
            "Orman" -> animationView.setAnimation("forest_animation.json")
            "Okyanus" -> animationView.setAnimation("ocean_animation.json")
            "Çöl" -> animationView.setAnimation("desert_animation.json")
            "Kutup" -> animationView.setAnimation("arctic_animation.json")
            else -> animationView.setAnimation("forest_animation.json")
        }
        
        // Animasyonu başlat
        animationView.playAnimation()
    }
    
    /**
     * Tür detaylarını gösterir
     */
    private fun showSpeciesDetails(species: Species) {
        // Tür detay sayfasına git
        val intent = Intent(activity, SpeciesDetailActivity::class.java).apply {
            putExtra("SPECIES_ID", species.id)
        }
        startActivity(intent)
        
        // Geçiş animasyonu
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    
    companion object {
        fun newInstance(ecosystemId: Int): EcosystemFragment {
            val fragment = EcosystemFragment()
            val args = Bundle()
            args.putInt("ECOSYSTEM_ID", ecosystemId)
            fragment.arguments = args
            return fragment
        }
    }
}
