package com.aakash.androidnutshell.usermodule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aakash.androidnutshell.AppApplication
import com.aakash.androidnutshell.R
import com.aakash.androidnutshell.userinfo.dto.UserInfoScreenData
import com.aakash.androidnutshell.userinfo.ui.UserInfoFragment
import com.aakash.androidnutshell.usermodule.dto.UserItemResponse
import com.aakash.androidnutshell.usermodule.model.UserItemsViewModel
import com.aakash.androidnutshell.utils.apicontract.ApiEndPoint
import kotlinx.android.synthetic.main.activity_user_items.*
import javax.inject.Inject

class UserItemsActivity : AppCompatActivity(){

    private lateinit var userViewModel: UserItemsViewModel
    private val fragmentMap = HashMap<String, UserInfoFragment>()
    private var subScreens = 0

    override fun onBackPressed() {
        if (subScreens != 0) {
            supportFragmentManager.popBackStackImmediate()
            subScreens --
        } else {
            super.onBackPressed()
        }
    }

    override fun invalidateOptionsMenu() {
        super.invalidateOptionsMenu()
    }

    @Inject
    lateinit var apiService: ApiEndPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_items)
        println("UserItemsActivity.onCreate")
        (application as AppApplication).appComponent.inject(this)
        userViewModel = ViewModelProvider(this, UserItemsViewModel.FACTORY(apiService))[UserItemsViewModel::class.java]
        setupApi()
    }

    override fun onStart() {
        super.onStart()
        println("UserItemsActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        println("UserItemsActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("UserItemsActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("UserItemsActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("UserItemsActivity.onDestroy")
    }

    private fun setupApi() {
        userViewModel.getUserListLiveData().observe(this, Observer {
            it.response?.let { userItems->
                rvUserItems.layoutManager = LinearLayoutManager(this)
                rvUserItems.adapter = UserItemsListAdapter(userItems) { position ->
                    openUserInfo(userItems[position])
                }
            }

        })
        btnClick.setOnClickListener {
            userViewModel.fetchUsers()
        }
    }

    private fun openUserInfo(userItemResponse: UserItemResponse) {
        val fragment = UserInfoFragment.newInstance(UserInfoScreenData(userItemResponse.login))
        userItemResponse.login?.let { login ->
            if (!fragmentMap.containsKey(login)) {
                fragmentMap[login] = fragment
                supportFragmentManager
                    .beginTransaction()
                    .replace(flContent.id, fragment, login)
                    .addToBackStack(login)
                    .commit()
                subScreens ++
            }
        }
    }
}