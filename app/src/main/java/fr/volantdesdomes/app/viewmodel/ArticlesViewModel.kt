package fr.volantdesdomes.app.viewmodel

import android.app.Application
import fr.volantdesdomes.app.model.WPPost
import fr.volantdesdomes.app.rest.APIHelper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.realm.RealmResults
import io.realm.Sort

class ArticlesViewModel(application: Application) : AbstractViewModel(application) {
    val flowable: Flowable<RealmResults<WPPost>> = realm.where(WPPost::class.java)
//            .sort(WPPost::date.name, Sort.DESCENDING)
            .findAllAsync()
            .asFlowable()
            .filter {
                it.isLoaded && it.isValid
            }

    fun refreshPosts() {

        APIHelper.instance.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElement()
                .onErrorComplete()
                .subscribe()
                .addTo(disposables)
    }
}
