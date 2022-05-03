package com.lft.hear4me

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.lft.hear4me.ui.login.LoginActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

open class Intent_Send : AppCompatActivity() {

    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intent_send_screen)
        if(intent?.action != null) {
            val file_extension_list: List<String> = intent.type.toString().split("/")
            val fileExtension: String = file_extension_list[0]
            if (fileExtension == "audio" && intent.action == Intent.ACTION_SEND) {
                handleSendAudio(intent)
                if (verify_logged_user() != true) {
                    val sendintent = Intent(this@Intent_Send, LoginActivity::class.java).apply {
                    }
                    startActivity(sendintent)
                }

                val progressBar = findViewById<ProgressBar>(R.id.upanprogressBar)
                runOnUiThread { progressBar.visibility = View.INVISIBLE }
                val analyzeButton = findViewById<Button>(R.id.AnalyzeButton)
                analyzeButton.setOnClickListener(View.OnClickListener {
                    if (verify_logged_user() != true) {
                        Toast.makeText(
                            applicationContext,
                            "Por favor, conclua seu cadastro antes de usar a função de análise de áudio.",
                            Toast.LENGTH_LONG
                        ).show()
                        val sendintent = Intent(this@Intent_Send, LoginActivity::class.java).apply {
                        }
                        startActivity(sendintent)
                    }else {
                        runOnUiThread { progressBar.visibility = View.VISIBLE }
                        findViewById<TextView>(R.id.Status_flag).text = "Carregando audio"
                        analyzeAudio(intent)
                    }
                })

            }
            }else {
                Toast.makeText(
                    applicationContext,
                    "Apenas arquivos com extensão .ogg são suportados!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun handleSendAudio(intent: Intent) {
        val play: ImageButton = findViewById<ImageButton>(R.id.playbutton)
        val pause: ImageButton = findViewById<ImageButton>(R.id.pausebutton)
        val stop: ImageButton = findViewById<ImageButton>(R.id.stopbutton)
        val receiveduri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)
        var stateplayer = 0
        mp = MediaPlayer()
        if (receiveduri != null) {
            mp!!.setDataSource(this, receiveduri)
            mp!!.prepare()
            //Inicia reprodução
            play.setOnClickListener(View.OnClickListener() {
                try {
                    if (stateplayer == 0 || stateplayer == 2) {
                        Toast.makeText(this, "Reproduzindo audio!", Toast.LENGTH_SHORT).show()
                        mp!!.start()
                        stateplayer = 1
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show()
                }
            })
            //Pausa reprodução
            pause.setOnClickListener(View.OnClickListener() {
                try {
                    if (stateplayer == 1) {
                        Toast.makeText(this, "Audio pausado!", Toast.LENGTH_SHORT).show()
                        mp!!.pause()
                        stateplayer = 2
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show()
                }
            })
            //Interrompe a reprodução
            stop.setOnClickListener(View.OnClickListener() {
                try {
                    if (stateplayer == 1 || stateplayer == 2) {
                        Toast.makeText(this, "Audio interrompido!", Toast.LENGTH_SHORT).show()
                        mp!!.stop()
                        mp!!.prepare()
                        stateplayer = 0
                    }
                } catch (ex: Exception) {
                    Toast.makeText(this, "Algo deu errado!", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

    private fun analyzeAudio(intent: Intent) {
        val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageReference: StorageReference = firebaseStorage.reference
        val receiveduri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)
        var extentionTermination = intent.type.toString().split("/")[1]
        extentionTermination = extentionTermination.toString().split(";")[0]
        val mycalendar = Calendar.getInstance()
        val year = mycalendar.get(Calendar.YEAR)
        val month = mycalendar.get(Calendar.MONTH)
        val day = mycalendar.get(Calendar.DAY_OF_MONTH)
        val hour = mycalendar.get(Calendar.HOUR_OF_DAY)
        val minute = mycalendar.get(Calendar.MINUTE)
        var auth = Firebase.auth
        val user = auth.currentUser
        val filename: String =
            ("Y${year}_M${month}_D${day}_H${hour}_M${minute}_rand${(0..1000000).random()}_app")
        val fileDest = "audio/${user?.uid}/${filename}.$extentionTermination"
        val audioStorageRef: StorageReference = storageReference.child(fileDest)
        //Tentando criar um listener
        val uploadTask = audioStorageRef.putFile(receiveduri!!)
        println(storageReference)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            audioStorageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                findViewById<TextView>(R.id.Status_flag).text = "Analisando Audio"
                findViewById<TextView>(R.id.Disclaimer_waiting).text =
                    "Esta atividade pode demorar um pouco, como este é um mvp, peço que não saia da página :D."
                callAPI(URL(downloadUri.toString()), "${filename}.$extentionTermination")
            } else {
                Toast.makeText(
                    this,
                    "Erro no upload do arquivo, verifique sua comunicação com a internet.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun callAPI(UrlFileDownload: URL, filename: String) {

        val baseUrl = "https://hear4me-hgwwoy3gea-uc.a.run.app/"

        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .callTimeout(1200, TimeUnit.SECONDS)
            .connectTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(1200, TimeUnit.SECONDS)
            .build()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        println(UrlFileDownload)
        println(filename)

        val retrofitdata = retrofitBuilder.getData(UrlFileDownload, filename)

        retrofitdata.enqueue(object : Callback<apiCallReturnItem?> {
            override fun onResponse(
                call: Call<apiCallReturnItem?>,
                response: Response<apiCallReturnItem?>
            ) {
                val responseBody = response.body()!!
                val progressBar = findViewById<ProgressBar>(R.id.upanprogressBar)
                responseBody.transcribedText
                runOnUiThread { progressBar.visibility = View.INVISIBLE }
                findViewById<TextView>(R.id.Status_flag).text = ""
                findViewById<TextView>(R.id.Disclaimer_waiting).text = ""

                val intentResponse =
                    Intent(this@Intent_Send, api_call_success::class.java).apply {
                        putExtra("text_analysis", responseBody.textAnalysis)
                        putExtra("text_transc", responseBody.transcribedText)
                    }
                startActivity(intentResponse)
            }

            override fun onFailure(call: Call<apiCallReturnItem?>, t: Throwable) {
                println("Falhou")
                Log.d("Intent_Send", "onFailure: " + t.message)
            }
        })
    }
    fun verify_logged_user(): Boolean {

        var auth = Firebase.auth
        val user = auth.currentUser
        return user != null
    }
}




