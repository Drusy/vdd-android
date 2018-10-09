package fr.volantdesdomes.app

import android.app.Application
import fr.volantdesdomes.app.rest.APIHelper
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class VDDApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Realm.init(this)
        APIHelper.init(this)
        Realm.setDefaultConfiguration(
                RealmConfiguration
                        .Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build()
        )
    }
}