package com.android.launcher.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelMain : ViewModel() {

    private val _loginStatus = MutableLiveData<Boolean>()

    val loginStatus: LiveData<Boolean> = _loginStatus



}