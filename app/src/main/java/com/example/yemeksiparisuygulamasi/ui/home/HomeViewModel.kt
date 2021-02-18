package com.example.yemeksiparisuygulamasi.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yemeksiparisuygulamasi.ui.common.BaseViewModel

class HomeViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}