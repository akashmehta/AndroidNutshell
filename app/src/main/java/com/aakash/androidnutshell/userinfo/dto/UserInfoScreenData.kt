package com.aakash.androidnutshell.userinfo.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoScreenData(
	val login: String? = null
) : Parcelable
