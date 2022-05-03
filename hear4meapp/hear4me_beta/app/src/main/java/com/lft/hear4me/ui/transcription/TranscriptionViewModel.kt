package com.lft.hear4me.ui.transcription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TranscriptionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Transcription Fragment"
    }
    val text: LiveData<String> = _text
}