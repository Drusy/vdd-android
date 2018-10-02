package fr.volantdesdomes.app.ext

import android.text.Html
import android.text.Spanned
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmModel

fun <T : List<RealmModel>> Single<T>.insertToRealm(beforeInsert: ((Realm, T) -> Unit)? = null): Single<T> {
    return doOnSuccess { value ->
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                beforeInsert?.invoke(it, value)
                it.insertOrUpdate(value)
            }
        }
    }
}

fun String.stripHtml(): Spanned = Html.fromHtml(this)