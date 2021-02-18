package com.example.yemeksiparisuygulamasi.ui.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yemeksiparisuygulamasi.ui.common.BaseViewModel

class DashboardViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}