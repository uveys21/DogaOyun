package com.dogakasifleri.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.dogakasifleri.R
import com.dogakasifleri.activities.SpeciesDetailActivity
import com.dogakasifleri.adapters.SpeciesAdapter
import com.dogakasifleri.models.Species
import com.dogakasifleri.viewmodels.EcosystemViewModel

class EcosystemFragment : Fragment() {

    private lateinit var ecosystemViewModel: EcosystemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var speciesAdapter: SpeciesAdapter
    private lateinit var ivEcosystemImage: ImageView
    private lateinit var tvSpeciesCount: TextView
    private lateinit var tvSpeciesCountText: TextView
    private lateinit var lottieAnimationView: LottieAnimationView

    private val ecosystemType: String by lazy {
        arguments?.getString("ecosystemType") ?: "Orman" // Varsayılan olarak "Orman"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ecosystem, container, false)

        ecosystemViewModel = ViewModelProvider(this)[EcosystemViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerViewSpecies)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        speciesAdapter = SpeciesAdapter(emptyList()) { species ->
            showSpeciesDetails(species)
        }
        recyclerView.adapter = speciesAdapter

        ivEcosystemImage = view.findViewById(R.id.ivEcosystemImage)
        return view
    }


    }
    
>>>>>>> Stashed changes
    private fun setupViewModel(){
        ecosystemViewModel.speciesList.observe(viewLifecycleOwner) { it ->
            if (it != null && it.isNotEmpty()) {
                speciesAdapter.updateData(it)
            } else {
                val newSpeciesList = mutableListOf<Species>()
                newSpeciesList.add(Species(1, "Meşe Ağacı", "Quercus robur", "Orman", "Yağmur ormanlarının en güçlü avcısı", R.drawable.species_oak_tree, "Meşe ağaçları, güçlü ve uzun ömürlü ağaçlardır. Palamut adı verilen meyveler üretirler.", "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.", 1, 0))
                newSpeciesList.add(Species(2, "Sincap", "Sciurus vulgaris", "Orman", "Yağmur ormanlarının en güçlü avcısı", R.drawable.species_squirrel, "Sincaplar, ağaçlarda yaşayan ve fındık, tohum gibi besinlerle beslenen kemirgenlerdir.", "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.", 1, 0))
                newSpeciesList.add(Species(3, "Geyik", "Cervus elaphus", "Orman", "Yağmur ormanlarının en güçlü avcısı", R.drawable.species_deer, "Geyikler, ormanlarda yaşayan otçul hayvanlardır. Erkek geyiklerin boynuzları vardır.", "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.", 1, 0))
                newSpeciesList.add(Species(4, "Mantar", "Agaricus bisporus", "Orman", "Yağmur ormanlarının en güçlü avcısı", R.drawable.species_mushroom, "Mantarlar, ormanlarda yetişen ve çürükçül beslenen organizmalardır.", "Keskin görüşe sahiptir ve yüksek yerlerde yaşar.", 1, 0))
                speciesAdapter.updateData(newSpeciesList)
            }
            if (tvSpeciesCount != null && tvSpeciesCountText != null){
                tvSpeciesCount.text = it?.size.toString()
                tvSpeciesCountText.text = if(it?.size == 1) "Tür" else "Türler"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvSpeciesCount = view.findViewById(R.id.tvSpeciesCount)
        tvSpeciesCountText = view.findViewById(R.id.tvSpeciesCountText)
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        

        setupViewModel()


        // Görseli ayarlama
        when(ecosystemType){
            "Orman" -> {
                ivEcosystemImage.setImageResource(R.drawable.bg_ecosystem_forest)
            }
            "Okyanus" -> {
                ivEcosystemImage.setImageResource(R.drawable.bg_ecosystem_ocean)
            }
            "Çöl" -> {
                ivEcosystemImage.setImageResource(R.drawable.bg_ecosystem_desert)
            }
            "Kutup" -> {
                ivEcosystemImage.setImageResource(R.drawable.banner_arctic)
            }
        }
        }

        // Animasyonları ayarlama
        when(ecosystemType){
            "Orman" -> {
                lottieAnimationView.setAnimation(R.raw.forest_animation)
            }
            "Okyanus" -> {
                lottieAnimationView.setAnimation(R.raw.ocean_animation)
            }
            "Çöl" -> {
                lottieAnimationView.setAnimation(R.raw.desert_animation)
            }
            "Kutup" -> {
                lottieAnimationView.setAnimation(R.raw.arctic_animation)
            }
            else -> {
                lottieAnimationView.setAnimation(R.raw.forest_animation)
            }
        }
        lottieAnimationView.playAnimation()
    }

    private fun showSpeciesDetails(species: Species) {
        val intent = Intent(requireContext(), SpeciesDetailActivity::class.java).apply {
            putExtra("SPECIES_ID", species.id)
        }
        startActivity(intent)
    }
}
