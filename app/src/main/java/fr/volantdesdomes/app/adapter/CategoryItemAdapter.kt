package fr.volantdesdomes.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.model.WPCategory
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var note: WPCategory? = null
        set(value) {
            field = value
            view.title.text = value?.name
            view.subtitle.text = value?.description
        }
}

class CategoryItemAdapter(val onClick: (WPCategory) -> Unit) :
    RecyclerView.Adapter<CategoryItemViewHolder>() {
    var items: List<WPCategory> = emptyList()

    fun loadItems(newItems: List<WPCategory>) {
        items = newItems
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder =
        CategoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item, parent, false)
        )

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.note = items[position]
        holder.view.setOnClickListener { onClick(items[position]) }
    }
}