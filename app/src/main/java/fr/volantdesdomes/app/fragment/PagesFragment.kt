package fr.volantdesdomes.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.viewmodel.PagesViewModel

class PagesFragment : AbstractFragment() {

    companion object {
        fun newInstance() = PagesFragment()
    }

    private lateinit var viewModel: PagesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pages, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PagesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
