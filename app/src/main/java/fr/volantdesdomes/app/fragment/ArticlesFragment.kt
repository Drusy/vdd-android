package fr.volantdesdomes.app.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.viewmodel.ArticlesViewModel

class ArticlesFragment : Fragment() {

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
        // TODO: Use the ViewModel
    }

}
