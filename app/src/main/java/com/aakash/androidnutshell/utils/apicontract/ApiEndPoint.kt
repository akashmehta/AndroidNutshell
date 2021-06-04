package com.aakash.androidnutshell.utils.apicontract

import com.aakash.androidnutshell.usermodule.dto.UserItemResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndPoint {

    @GET("users")
    fun  fetchUsers() : Observable<ArrayList<UserItemResponse>>


    @GET("users/{username}")
    fun  fetchUser(@Path("username") name: String) : Observable<UserItemResponse>
}