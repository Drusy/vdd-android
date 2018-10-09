package fr.volantdesdomes.app.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import fr.volantdesdomes.app.R
import fr.volantdesdomes.app.rest.APIHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = nav_host_fragment as NavHostFragment
        bottom_navigation_view.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
        }

        APIHelper.activityIndicatorSubject
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it != 0) {
//                    toolbar_progressbar.visibility = View.VISIBLE
                    toolbar_horizontal_progressbar.visibility = View.VISIBLE
                } else {
//                    toolbar_progressbar.visibility = View.GONE
                    toolbar_horizontal_progressbar.visibility = View.GONE
                }
            }
            .addTo(disposables)
    }
}
