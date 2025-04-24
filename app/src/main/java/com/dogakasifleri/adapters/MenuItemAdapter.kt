package com.dogakasifleri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dogakasifleri.R
import com.dogakasifleri.models.MenuItem

/**
 * Menü Öğesi Adapter - RecyclerView'da ana menü öğelerini göstermek için kullanılır
 */
class MenuItemAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val currentMenuItem = menuItems[position]
        holder.bind(currentMenuItem)
    }

    override fun getItemCount(): Int = menuItems.size

    inner class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMenuIcon: ImageView = itemView.findViewById(R.id.ivMenuIcon)
        private val tvMenuTitle: TextView = itemView.findViewById(R.id.tvMenuTitle)
        private val tvMenuDescription: TextView = itemView.findViewById(R.id.tvMenuDescription)

        fun bind(menuItem: MenuItem) {
            ivMenuIcon.setImageResource(menuItem.iconResId)
            tvMenuTitle.text = menuItem.title
            tvMenuDescription.text = menuItem.description

            // Öğe tıklama olayı
            itemView.setOnClickListener {
                onItemClick(menuItem)
            }
        }
    }
}
