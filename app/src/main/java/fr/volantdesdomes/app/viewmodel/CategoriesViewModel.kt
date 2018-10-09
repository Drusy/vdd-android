package fr.volantdesdomes.app.viewmodel

import android.app.Application
import fr.volantdesdomes.app.model.WPCategory
import fr.volantdesdomes.app.rest.APIHelper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.realm.RealmResults

class CategoriesViewModel(application: Application) : AbstractViewModel(application) {
    val flowable: Flowable<RealmResults<WPCategory>> = realm.where(WPCategory::class.java)
        .greaterThan(WPCategory::count.name, 0)
        .findAllAsync()
        .asFlowable()
        .filter {
            it.isLoaded && it.isValid
        }

    fun refresh() {
        APIHelper.getAllCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .ignoreElement()
            .onErrorComplete()
            .subscribe()
            .addTo(disposables)
    }
}
