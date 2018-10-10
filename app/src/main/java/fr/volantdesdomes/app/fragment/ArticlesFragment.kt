package fr.volantdesdomes.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.adapter.PostItemAdapter
import fr.volantdesdomes.app.model.WPCategory
import fr.volantdesdomes.app.viewmodel.ArticlesViewModel
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_articles.*
import timber.log.Timber

class ArticlesFragment : AbstractFragment() {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private lateinit var viewModel: ArticlesViewModel
    private var category: WPCategory? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        category = arguments?.get("category") as WPCategory

        val adapter = PostItemAdapter {
            Timber.d(it.title?.rendered)
        }

        recycler_articles.adapter = adapter
        recycler_articles.layoutManager = LinearLayoutManager(this.context).apply {
            orientation = RecyclerView.VERTICAL
        }

        loader.visibility = View.VISIBLE
        recycler_articles.visibility = View.GONE

        viewModel.flowable.subscribe {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()

            recycler_articles.visibility = View.GONE
            empty_view.visibility = View.GONE
            loader.visibility = View.GONE

            if (it.isEmpty()) {
                empty_view.visibility = View.VISIBLE

            } else {
                recycler_articles.visibility = View.VISIBLE
            }
        }.addTo(disposables)

        if (category != null) {
            Timber.d("Category ${category?.name} articles")
        } else {
            Timber.d("All articles")
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.refresh()
    }
}
