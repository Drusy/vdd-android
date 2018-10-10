package fr.volantdesdomes.app.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import fr.volantdesdomes.app.VDDApplication
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm

abstract class AbstractViewModel(application: Application) : AndroidViewModel(application) {
    protected val disposables = CompositeDisposable()

    val realm = Realm.getDefaultInstance()

    override fun onCleared() {
        super.onCleared()

        disposables.dispose()
        realm.close()
    }

    protected val context: Context
        get() = getApplication<VDDApplication>().applicationContext
}