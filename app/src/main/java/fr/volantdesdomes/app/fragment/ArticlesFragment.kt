package fr.volantdesdomes.app.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.adapter.PostItemAdapter
import fr.volantdesdomes.app.viewmodel.ArticlesViewModel
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_articles.*
import timber.log.Timber

class ArticlesFragment : AbstractFragment() {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private lateinit var viewModel: ArticlesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)

        val adapter = PostItemAdapter {
//            Timber.d(it.title?.rendered)
        }

        recycler_articles.adapter = adapter
        recycler_articles.layoutManager = LinearLayoutManager(this.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        loader.visibility = View.VISIBLE
        recycler_articles.visibility = View.GONE

        viewModel.flowable.subscribe {
            adapter.loadItems(it ?: emptyList())
            adapter.notifyDataSetChanged()

            if (it.isEmpty()) {
                viewModel.refreshPosts()
            } else {
                loader.visibility = View.GONE
                recycler_articles.visibility = View.VISIBLE
            }
        }.addTo(disposables)
    }
}
