package com.lft.hear4me.ui.audio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AudioViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Audio Fragment"
    }
    val text: LiveData<String> = _text
}