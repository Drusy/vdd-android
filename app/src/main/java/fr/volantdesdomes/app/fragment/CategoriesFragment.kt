package fr.volantdesdomes.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.adapter.CategoryItemAdapter
import fr.volantdesdomes.app.viewmodel.CategoriesViewModel
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_categories.*
import timber.log.Timber

class CategoriesFragment : AbstractFragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)


        val adapter = CategoryItemAdapter {
            Timber.d("Category ${it.name} selected")
            val bundle = bundleOf(
                "category" to it
            )
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_category_to_navigation_articles, bundle)
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
    }

    override fun onStart() {
        super.onStart()

        viewModel.refresh()
    }
}
