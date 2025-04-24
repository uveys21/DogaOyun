package com.dogakasifleri.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.dogakasifleri.models.Ecosystem
import com.dogakasifleri.utils.AnimationUtils

/**
 * MapFragment - Kullanıcının farklı ekosistemleri harita üzerinde görebileceği fragment
 * Bu fragment, dünya haritası üzerinde farklı ekosistemleri işaretler ve kullanıcının
 * bu ekosistemlere tıklayarak detaylarına erişmesini sağlar.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var ecosystems: List<Ecosystem> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        
        // Harita fragment'ını başlat
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        
        // Harita tipini ayarla (uydu görünümü çocuklar için daha ilgi çekici olabilir)
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        
        // Ekosistemleri yükle
        loadEcosystems()
        
        // Ekosistemleri haritaya ekle
        addEcosystemsToMap()
        
        // Harita etkileşimlerini ayarla
        setupMapInteractions()
        
        // Başlangıç konumunu ayarla (dünya geneli görünüm)
        val worldCenter = LatLng(0.0, 0.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(worldCenter, 2f))
        
        // Harita animasyonunu uygula
        AnimationUtils.applyMapAnimation(view?.findViewById(R.id.map))
    }
    
    /**
     * Ekosistemleri yükler
     */
    private fun loadEcosystems() {
        // Normalde bu veriler bir veritabanından veya API'den gelir
        // Şimdilik örnek veriler kullanıyoruz
        ecosystems = listOf(
            Ecosystem(
                1,
                "Amazon Yağmur Ormanı",
                "Orman",
                LatLng(-3.4653, -62.2159),
                "Dünyanın en büyük yağmur ormanı",
                0
            ),
            Ecosystem(
                2,
                "Büyük Mercan Resifi",
                "Okyanus",
                LatLng(-18.2871, 147.6992),
                "Dünyanın en büyük mercan resifi",
                0
            ),
            Ecosystem(
                3,
                "Sahra Çölü",
                "Çöl",
                LatLng(23.4162, 25.6628),
                "Dünyanın en büyük sıcak çölü",
                0
            ),
            Ecosystem(
                4,
                "Antarktika",
                "Kutup",
                LatLng(-79.4063, 0.3149),
                "Dünyanın en soğuk kıtası",
                0
            )
        )
    }
    
    /**
     * Ekosistemleri haritaya ekler
     */
    private fun addEcosystemsToMap() {
        for (ecosystem in ecosystems) {
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(ecosystem.location)
                    .title(ecosystem.name)
                    .snippet(ecosystem.description)
            )
            marker?.tag = ecosystem.id
        }
    }
    
    /**
     * Harita etkileşimlerini ayarlar
     */
    private fun setupMapInteractions() {
        // Marker tıklama olayını dinle
        mMap.setOnMarkerClickListener { marker ->
            // Marker'a tıklandığında ekosistem detaylarını göster
            val ecosystemId = marker.tag as Int
            showEcosystemDetails(ecosystemId)
            true
        }
        
        // Harita tıklama olayını dinle
        mMap.setOnMapClickListener { latLng ->
            // Haritaya tıklandığında en yakın ekosistemi bul
            val nearestEcosystem = findNearestEcosystem(latLng)
            if (nearestEcosystem != null) {
                // Ekosistem detaylarını göster
                showEcosystemDetails(nearestEcosystem.id)
            }
        }
    }
    
    /**
     * Ekosistem detaylarını gösterir
     */
    private fun showEcosystemDetails(ecosystemId: Int) {
        // Ekosistem detay sayfasına git
        val intent = Intent(activity, EcosystemActivity::class.java).apply {
            putExtra("ECOSYSTEM_ID", ecosystemId)
        }
        startActivity(intent)
        
        // Geçiş animasyonu
        activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
    
    /**
     * Verilen konuma en yakın ekosistemi bulur
     */
    private fun findNearestEcosystem(latLng: LatLng): Ecosystem? {
        if (ecosystems.isEmpty()) return null
        
        var nearestEcosystem = ecosystems[0]
        var minDistance = calculateDistance(latLng, nearestEcosystem.location)
        
        for (i in 1 until ecosystems.size) {
            val distance = calculateDistance(latLng, ecosystems[i].location)
            if (distance < minDistance) {
                minDistance = distance
                nearestEcosystem = ecosystems[i]
            }
        }
        
        // Eğer mesafe çok uzaksa null dön
        return if (minDistance > 20) null else nearestEcosystem
    }
    
    /**
     * İki konum arasındaki mesafeyi hesaplar (basitleştirilmiş)
     */
    private fun calculateDistance(latLng1: LatLng, latLng2: LatLng): Double {
        val lat1 = latLng1.latitude
        val lon1 = latLng1.longitude
        val lat2 = latLng2.latitude
        val lon2 = latLng2.longitude
        
        // Basit Öklid mesafesi (gerçek uygulamada Haversine formülü kullanılmalı)
        return Math.sqrt(Math.pow(lat1 - lat2, 2.0) + Math.pow(lon1 - lon2, 2.0))
    }
}
