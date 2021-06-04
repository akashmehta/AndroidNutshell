package com.aakash.androidnutshell.usermodule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aakash.androidnutshell.AppApplication
import com.aakash.androidnutshell.R
import com.aakash.androidnutshell.usermodule.model.UserItemsViewModel
import com.aakash.androidnutshell.utils.apicontract.ApiEndPoint
import kotlinx.android.synthetic.main.activity_user_items.*
import javax.inject.Inject

class UserItemsActivity : AppCompatActivity(){

    private lateinit var userViewModel: UserItemsViewModel

    @Inject
    lateinit var apiService: ApiEndPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_items)
        (application as AppApplication).appComponent.inject(this)
        userViewModel = ViewModelProvider(this, UserItemsViewModel.FACTORY(apiService))[UserItemsViewModel::class.java]
        setupApi()
    }

    private fun setupApi() {
        userViewModel.getLiveData().observe(this, Observer {
            it.response?.let { userItems->
                rvUserItems.layoutManager = LinearLayoutManager(this)
                rvUserItems.adapter = UserItemsListAdapter(userItems)
            }

        })
        userViewModel.fetchUsers()
    }
}