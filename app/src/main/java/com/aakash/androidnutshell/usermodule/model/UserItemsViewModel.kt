package com.aakash.androidnutshell.usermodule.model

import android.util.Pair
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aakash.androidnutshell.usermodule.dto.UserItemResponse
import com.aakash.androidnutshell.AppApplication
import com.aakash.androidnutshell.utils.CommonApiResponse
import com.aakash.androidnutshell.utils.Constants
import com.aakash.androidnutshell.utils.apicontract.ApiEndPoint
import com.aakash.androidnutshell.utils.singleArgViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class UserItemsViewModel(var apiService: ApiEndPoint) : ViewModel(), LifecycleObserver {

    companion object {
        val FACTORY =
            singleArgViewModel(
                ::UserItemsViewModel
            )
    }

    private val compositeDisposable = CompositeDisposable()
    private val liveData = MutableLiveData<CommonApiResponse<ArrayList<UserItemResponse>>>()

    fun getLiveData()  = liveData

    fun fetchUsers() {
        if (AppApplication.getInstance().hasNetworkConnection()) {
            liveData.value = CommonApiResponse(progress = true)

            val disposable = apiService.fetchUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    liveData.value = CommonApiResponse(response = item)
                }, {
                    liveData.value = CommonApiResponse(error = Pair(-1,""))
                })
            compositeDisposable.add(disposable)
        } else {
            liveData.value = CommonApiResponse(error = Pair(Constants.ErrorStates.NETWORK_ERROR, ""))
        }
    }

    fun fetchSingleUser() {
        if (AppApplication.getInstance().hasNetworkConnection()) {
            liveData.value = CommonApiResponse(progress = true)

            val disposable = apiService.fetchUsers()
                .observeOn(Schedulers.io())
                .switchMap { itemList ->
                    apiService.fetchUser(itemList[0].login ?: "")
                        .materialize()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    println("value = ${Objects.toString(item)}")
                }, {
                    liveData.value = CommonApiResponse(error = Pair(-1,""))
                })
            compositeDisposable.add(disposable)
        } else {
            liveData.value = CommonApiResponse(error = Pair(Constants.ErrorStates.NETWORK_ERROR, ""))
        }

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}