package com.lft.hear4me.ui.premium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PremiumViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Premium Fragment"
    }
    val text: LiveData<String> = _text
}