package com.lft.hear4me

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception

//class Intent_Send : AppCompatActivity() {
//
//    var mp:MediaPlayer?=null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.intent_send_screen)
//        val receiveAudioIntent=intent.extras
//        val intent=receiveAudioIntent?.get("audiointent")
////        ("audiointent")
//        val file_extension_list: List<String> = intent.type.toString().split("/")
//        val intent_action_list: List<String> = intent.action.toString().split(".")
//        val fileExtension: String = file_extension_list[0]
//        if (fileExtension == "audio" && intent.action == Intent.ACTION_SEND) {
//            handleSendAudio(intent)
//        } else {
//                UnsuportedFormatRequest(intent)
//        }
//
//    }
//
//    private fun handleSendAudio(intent: Intent) {
//        val one: Button = findViewById<Button>(R.id.buttonteste)
//        val receiveduri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)
//        one.setOnClickListener(View.OnClickListener() {
//            Toast.makeText(this,"Botão apertado!", Toast.LENGTH_LONG).show()
//            mp = MediaPlayer()
//            try{
//                findViewById<TextView>(R.id.textView1).text = intent.getStringExtra(Intent.EXTRA_TEXT)
//                if (receiveduri != null) {
//                    mp!!.setDataSource(this,receiveduri)
//                    mp!!.prepare()
//                    mp!!.start()
//                }
//            }catch(ex: Exception){
//                findViewById<TextView>(R.id.textView1).text = "Erro na criação do mp"
//            }
//        })
//    }
//
//    private fun UnsuportedFormatRequest(intent: Intent) {
//        TODO("Movimentar pessoa para página de bad request indicando que o formato enviado por ela não é aceito.")
//    }
//
//}
