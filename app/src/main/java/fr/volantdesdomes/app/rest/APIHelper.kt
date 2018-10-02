package fr.volantdesdomes.app.rest

import com.squareup.moshi.Moshi
import fr.volantdesdomes.app.ext.insertToRealm
import fr.volantdesdomes.app.model.WPPost
import fr.volantdesdomes.app.rest.adapter.DateJsonAdapter
import fr.volantdesdomes.app.rest.adapter.RealmListJsonAdapterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*


class APIHelper private constructor() {

    private object Singleton {
        val INSTANCE = APIHelper()
    }



    private var service: WPService = Retrofit.Builder()
            .baseUrl("https://volantdesdomes.fr/index.php/wp-json/wp/v2/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().apply {
                add(Date::class.java, DateJsonAdapter().nullSafe())
                add(RealmListJsonAdapterFactory())
            }.build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(WPService::class.java)

    companion object {
        val instance: APIHelper by lazy { Singleton.INSTANCE }
    }

    fun getPosts(categories: Array<Int>? = null, page: Int? = null, perPage: Int? = null): Single<List<WPPost>> {
        return service
                .getPosts(categories = categories, perPage = perPage, page = page)
                .insertToRealm()
                .doOnError {
                    Timber.e(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
    }
}