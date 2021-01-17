package com.ref.hiltfeaturemodule.repository

import kotlinx.coroutines.Dispatchers

class CoroutineHelper {
    fun fetchCoroutineDispatcher() = Dispatchers.IO
}