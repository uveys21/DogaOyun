package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.MenuItemAdapter
import com.dogakasifleri.models.MenuItem

/**
 * Ana Menü Fragment - Uygulamanın ana menüsünü gösterir
 */
class MainMenuFragment : Fragment() {

    private lateinit var rvMenuItems: RecyclerView
    private lateinit var btnStartAdventure: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unu inflate et
        val view = inflater.inflate(R.layout.fragment_main_menu, container, false)

        // UI elemanlarını tanımla
        rvMenuItems = view.findViewById(R.id.rvMenuItems)
        btnStartAdventure = view.findViewById(R.id.btnStartAdventure)

        // Menü öğelerini hazırla
        setupMenuItems()

        // Maceraya başla butonu tıklama olayı
        btnStartAdventure.setOnClickListener {
            startAdventure()
        }

        return view
    }

    private fun setupMenuItems() {
        // Menü öğeleri
        val menuItems = listOf(
            MenuItem(1, "Harita", R.drawable.menu_map, "Farklı ekosistemleri keşfet"),
            MenuItem(2, "Koleksiyon", R.drawable.menu_collection, "Keşfettiğin türleri görüntüle"),
            MenuItem(3, "Başarılar", R.drawable.menu_achievements, "Kazandığın rozetleri gör"),
            MenuItem(4, "Ayarlar", R.drawable.menu_settings, "Uygulama ayarlarını değiştir")
        )

        // Adapter'ı ayarla
        val adapter = MenuItemAdapter(menuItems) { menuItem ->
            // Menü öğesi seçildiğinde
            handleMenuItemClick(menuItem)
        }

        // RecyclerView'ı ayarla
        rvMenuItems.layoutManager = GridLayoutManager(context, 2)
        rvMenuItems.adapter = adapter
    }

    private fun handleMenuItemClick(menuItem: MenuItem) {
        // Seçilen menü öğesine göre işlem yap
        when (menuItem.id) {
            1 -> { // Harita
                val intent = android.content.Intent(activity, com.dogakasifleri.activities.MapActivity::class.java)
                startActivity(intent)
            }
            2 -> { // Koleksiyon
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, CollectionFragment())
                    ?.commit()
            }
            3 -> { // Başarılar
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, AchievementsFragment())
                    ?.commit()
            }
            4 -> { // Ayarlar
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, SettingsFragment())
                    ?.commit()
            }
        }
    }

    private fun startAdventure() {
        // Harita ekranını aç
        val intent = android.content.Intent(activity, com.dogakasifleri.activities.MapActivity::class.java)
        startActivity(intent)
    }
}
