package fr.volantdesdomes.app.rest

import android.content.Context
import com.squareup.moshi.Moshi
import fr.volantdesdomes.app.ext.insertToRealm
import fr.volantdesdomes.app.model.WPCategory
import fr.volantdesdomes.app.model.WPPost
import fr.volantdesdomes.app.rest.adapter.DateJsonAdapter
import fr.volantdesdomes.app.rest.adapter.RealmListJsonAdapterFactory
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import okhttp3.Cache
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.EventListener;
import java.io.IOException

object APIHelper {
    val activityIndicatorSubject: BehaviorSubject<Int> = BehaviorSubject.createDefault(0)
    lateinit var service: WPService

    fun init(context: Context) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val cache = Cache(context.cacheDir, 10 * 1024 * 1024 ) // 10 MiB
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .eventListener(object: EventListener() {
                override fun callStart(call: Call) {
                    super.callStart(call)

                    startActivity()
                }

                override fun callEnd(call: Call) {
                    super.callEnd(call)

                    stopActivity()
                }

                override fun callFailed(call: Call, ioe: IOException) {
                    super.callFailed(call, ioe)

                    stopActivity()
                }

            })
            .build()


        service = Retrofit.Builder()
            .baseUrl("https://volantdesdomes.fr/wp-json/wp/v2/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().apply {
                add(Date::class.java, DateJsonAdapter().nullSafe())
                add(RealmListJsonAdapterFactory())
            }.build()))
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(WPService::class.java)
    }

    private fun startActivity() {
        val current = activityIndicatorSubject.value ?: 0

        activityIndicatorSubject.onNext(current + 1)
    }

    private fun stopActivity() {
        val current = activityIndicatorSubject.value ?: 0

        activityIndicatorSubject.onNext(current - 1)
    }

    fun isLoading(): Boolean {
        return activityIndicatorSubject.value != 0
    }

    fun getAllPosts(): Single<List<WPPost>> {
        return service
            .getPosts(perPage = 100)
            .insertToRealm()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Timber.e(it)
            }
    }

    fun getAllCategories(): Single<List<WPCategory>> {
        return service
            .getCategories(perPage = 100)
            .insertToRealm()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Timber.e(it)
            }
    }
}