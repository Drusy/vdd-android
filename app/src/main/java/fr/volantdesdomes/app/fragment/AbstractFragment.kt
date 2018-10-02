package fr.volantdesdomes.app.fragment

import android.support.v4.app.Fragment
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