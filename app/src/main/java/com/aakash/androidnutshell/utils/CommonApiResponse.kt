package com.aakash.androidnutshell.utils

import android.util.Pair

data class CommonApiResponse<T>(val progress: Boolean = false,
                               val response: T? = null,
                               val error: Pair<Int, String>? = null)