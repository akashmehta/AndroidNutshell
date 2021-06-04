package com.aakash.androidnutshell

import android.content.Context
import android.net.ConnectivityManager
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.aakash.androidnutshell.utils.dagger.AppComponent
import com.aakash.androidnutshell.utils.dagger.DaggerAppComponent
import com.aakash.androidnutshell.utils.dagger.RetrofitModules

class AppApplication : MultiDexApplication() {

    lateinit var appComponent : AppComponent
    companion object {
        private lateinit var application : AppApplication
        fun getInstance() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        setupMultiDex()
        setupDagger()
    }

    private fun setupDagger() {
        appComponent = DaggerAppComponent.builder()
            .retrofitModules(RetrofitModules())
            .build()
    }

    private fun setupMultiDex() {
        MultiDex.install(this)
    }


    /**
     * Checking the internet connectivity
     *
     * @return true if the connection is available otherwise false
     */
    fun hasNetworkConnection(): Boolean {
        val activeNetwork =
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}