package fr.volantdesdomes.app.fragment

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractFragment : Fragment() {
    protected var disposables = CompositeDisposable()

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}