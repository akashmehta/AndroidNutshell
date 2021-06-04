package com.aakash.androidnutshell.utils.dagger

import com.aakash.androidnutshell.userinfo.ui.UserInfoFragment
import com.aakash.androidnutshell.usermodule.ui.UserItemsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModules::class])
interface AppComponent {
    fun inject(activity: UserItemsActivity)
    fun inject(fragment: UserInfoFragment)
}