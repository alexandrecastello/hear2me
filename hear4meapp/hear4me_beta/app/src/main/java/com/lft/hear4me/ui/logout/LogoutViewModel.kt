package com.lft.hear4me.ui.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Você tem certeza que deseja realizar o logout?"
    }
    val text: LiveData<String> = _text

}