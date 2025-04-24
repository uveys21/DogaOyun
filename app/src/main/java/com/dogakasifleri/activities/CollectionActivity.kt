package com.dogakasifleri.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar // ProgressBar importu
import android.widget.Toast // Hata mesajları için
import androidx.activity.viewModels // by viewModels için import
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer // Observer importu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.adapters.CollectionAdapter // Bu Adapter'ın CollectionItem ile çalıştığını varsayıyoruz
import com.dogakasifleri.models.CollectionItem
import com.dogakasifleri.utils.AnimationUtils
import com.dogakasifleri.viewmodels.CollectionViewModel // ViewModel importu

class CollectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CollectionAdapter
    private lateinit var emptyView: View // Boş görünüm için referans
    private lateinit var progressBar: ProgressBar // Yükleme göstergesi

    // ViewModel'i activity-ktx kullanarak başlatma (Tavsiye edilen yol)
    private val viewModel: CollectionViewModel by viewModels()

    // Adapter için liste (Adapter'ın bir update metodu olduğunu varsayacağız)
    private var currentCollectionItems: MutableList<CollectionItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        // UI Elementlerini Bul
        recyclerView = findViewById(R.id.recyclerViewCollection)
        emptyView = findViewById(R.id.emptyCollectionView) // Layout'taki ID ile eşleşmeli
        progressBar = findViewById(R.id.progressBar) // Layout'a ProgressBar eklemelisiniz (ID: progressBar)

        // Toolbar ayarları
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Koleksiyonum"

        // RecyclerView ve Adapter Ayarları
        setupRecyclerView()

        // LiveData Gözlemcilerini Ayarla
        observeViewModel()

        // Veriyi Yükle (ViewModel aracılığıyla)
        // TODO: "USER_ID_HERE" kısmını gerçek kullanıcı ID'si ile değiştirin
        viewModel.loadAllCollectionItems("USER_ID_HERE")

        // Animasyonları uygula (isteğe bağlı, RecyclerView ayarlandıktan sonra)
        AnimationUtils.applyItemAnimations(recyclerView)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        // Adapter'ı BAŞLANGIÇTA BOŞ liste ile oluşturun.
        // CollectionAdapter'ın CollectionItem listesi ve bir lambda beklediğini varsayıyoruz.
        // Ayrıca adapter'ın veriyi güncellemek için bir metodu olmalı (örn: updateData)
        adapter = CollectionAdapter(this, currentCollectionItems) { item ->
            showItemDetails(item)
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        // Filtrelenmiş (gösterilecek) öğeleri gözlemle
        viewModel.filteredItems.observe(this, Observer { items ->
            Log.d("CollectionActivity", "Filtered items updated: ${items?.size ?: 0} items")
            items?.let {
                // Adapter'ın verisini güncelle (Adapter'a böyle bir metod eklemeniz gerekebilir)
                adapter.updateData(it) // Bu metodu CollectionAdapter'a ekleyin!
                updateEmptyViewVisibility(it.isEmpty())
            }
        })

        // Yükleme durumunu gözlemle
        viewModel.isLoading.observe(this, Observer { isLoading ->
            Log.d("CollectionActivity", "isLoading state: $isLoading")
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            // Yüklenirken RecyclerView'ı gizlemek isteyebilirsiniz
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            // Yüklenirken boş görünümü de gizle
            if (isLoading) emptyView.visibility = View.GONE
        })

        // Hata durumunu gözlemle
        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                if (it.isNotEmpty()) {
                    Log.e("CollectionActivity", "Error observed: $it")
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    // Hata gösterildikten sonra ViewModel'deki hatayı temizlemek isteyebilirsiniz
                    // viewModel.clearError() // ViewModel'e böyle bir fonksiyon ekleyebilirsiniz
                }
            }
        })

        // İsteğe bağlı: Favori listesini ayrı bir yerde kullanmayacaksanız gözlemlemeyebilirsiniz.
        /*
        viewModel.favoriteItems.observe(this, Observer { favoriteItems ->
            // Belki bir sayaç güncellemesi veya başka bir UI elemanı için
            Log.d("CollectionActivity", "Favorite items count: ${favoriteItems?.size ?: 0}")
        })
        */
    }

    // Adapter'ı güncellemek için yardımcı fonksiyon (CollectionAdapter'da olmalı)
    // Örnek: CollectionAdapter.kt içine ekleyin
    /*
    fun updateData(newItems: List<CollectionItem>) {
        this.items.clear() // Adapter içindeki liste
        this.items.addAll(newItems)
        notifyDataSetChanged() // Veya DiffUtil kullanıyorsanız submitList
    }
    */


    private fun updateEmptyViewVisibility(isEmpty: Boolean) {
        // Sadece yükleme bittikten sonra boş görünümü kontrol et
        if (viewModel.isLoading.value == false) {
            emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
        } else {
            // Yüklenirken ikisi de gizli olabilir (ProgressBar görünür)
            emptyView.visibility = View.GONE
            // recyclerView.visibility = View.GONE // Zaten isLoading observer'ında yapılıyor
        }
    }

    /**
     * Seçilen koleksiyon öğesinin detaylarını gösterir
     */
    private fun showItemDetails(item: CollectionItem) {
        val intent = Intent(this, SpeciesDetailActivity::class.java).apply {
            // SpeciesDetailActivity'nin hangi veriyi beklediğine göre ayarlayın
            putExtra("ITEM_ID", item.id)
            // Belki referans ID'yi de göndermek istersiniz?
            // putExtra("SPECIES_REF_ID", item.referenceId)
            // Veya tüm nesneyi (Serializable olduğu için gönderebilirsiniz)
            // putExtra("COLLECTION_ITEM", item) // SpeciesDetailActivity'nin bunu alması gerekir
        }
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}