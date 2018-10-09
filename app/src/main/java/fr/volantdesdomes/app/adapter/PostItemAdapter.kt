package fr.volantdesdomes.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.ext.stripHtml
import fr.volantdesdomes.app.model.WPPost
import kotlinx.android.synthetic.main.post_item.view.*

class PostItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    var note: WPPost? = null
        set(value) {
            field = value
            view.title.text = value?.strippedTitle
            view.content.text = value?.strippedExcerp
        }
}

class PostItemAdapter(val onClick: (WPPost) -> Unit) : RecyclerView.Adapter<PostItemViewHolder>() {
    var items: List<WPPost> = emptyList()

    fun loadItems(newItems: List<WPPost>) {
        items = newItems
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder =
        PostItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_item, parent, false)
        )

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        holder.note = items[position]
        holder.view.setOnClickListener { onClick(items[position]) }
    }
}