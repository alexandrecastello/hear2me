package com.lft.hear4me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.media.MediaPlayer


class MainActivity : AppCompatActivity() {

    var mp:MediaPlayer?=null
    val IMAGE_URI_KEY = "IMAGE_URI"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent?.action != null) {
            val file_extension_list: List<String> = intent.type.toString().split("/")
            val fileExtension: String = file_extension_list[0]
            if (fileExtension == "audio" && intent.action == Intent.ACTION_SEND) {
                val sendintent = Intent(this@MainActivity,Intent_Send::class.java).apply {
                    putExtra(IMAGE_URI_KEY, intent)
                }
                startActivity(sendintent)
            }
            else {
//                UnsuportedFormatRequest(intent)
            }

        }else {
//            DirectOpeningProcedure()
        }
    }

    private fun DirectOpeningProcedure() {
        TODO("Movimentar para a página de upload de audio.")
    }


    private fun UnsuportedFormatRequest(intent: Intent) {
        TODO("Movimentar pessoa para página de bad request indicando que o formato enviado por ela não é aceito.")
    }

}
