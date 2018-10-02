package fr.volantdesdomes.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.ext.stripHtml
import fr.volantdesdomes.app.model.WPPost
import kotlinx.android.synthetic.main.post_item.view.*

class ListItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var note: WPPost? = null
        set(value) {
            field = value
            view.title.text = value?.title?.rendered?.stripHtml()
            view.excerpt.text = value?.excerpt?.rendered?.stripHtml()
        }
}

class PostItemAdapter(val onClick: (WPPost) -> Unit) : RecyclerView.Adapter<ListItemViewHolder>() {
    var items: List<WPPost> = emptyList()

    fun loadItems(newItems: List<WPPost>) {
        items = newItems
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder = ListItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false))

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.note = items[position]
        holder.view.setOnClickListener { onClick(items[position]) }
    }
}