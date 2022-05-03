package com.lft.hear4me

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class api_call_success: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.api_call_success_landing)
        val text_analysis: String? =intent.getStringExtra("text_analysis")
        val text_transcription: String? =intent.getStringExtra("text_transc")
        println(text_transcription)
        println(text_analysis)
        findViewById<TextView>(R.id.Text_Analysis).text=text_analysis
        findViewById<TextView>(R.id.Text_Transcription).text=text_transcription


    }
}