package com.lft.hear4me

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.lft.hear4me.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    var mp: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarHome.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        val headerView = navView.getHeaderView(0)


        if(verify_logged_user())
        {
            var auth = Firebase.auth
            val user = auth.currentUser
            println(user!!.email.toString())
            headerView.findViewById<TextView>(R.id.UserTextView).text = user.displayName
            headerView.findViewById<TextView>(R.id.emailTextView).text = user.email
            println("Batata")
            println(user.photoUrl)
            println(user.displayName)
            headerView.findViewById<ImageView>(R.id.photoUser).setImageURI(user.photoUrl)
            navView.menu.findItem(R.id.nav_login).isVisible = false
            navView.menu.findItem(R.id.nav_logout).isVisible = true
        }else{
            navView.menu.findItem(R.id.nav_logout).isVisible = false
            navView.menu.findItem(R.id.nav_login).isVisible = true
        }

        //Realizar upload
        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //Determina a cor dos icones da navbar
        navView.itemIconTintList = null
        //Cria a barra de acesso entre as páginas (O Login não possui propositalmente)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_audio, R.id.nav_transcription, R.id.nav_premium
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        headerView.findViewById<Button>(R.id.profilePicButton).setOnClickListener {

            var imgView=headerView.findViewById<ImageView>(R.id.photoUser)
            var i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i,101)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        var imgView=headerView.findViewById<ImageView>(R.id.photoUser)
        if(requestCode==101) {
            var pic=data?.getParcelableExtra<Bitmap>("data")
            imgView.setImageBitmap(pic)
            val profileUpdates = userProfileChangeRequest {
                photoUri = data?.data
            }
        }

    }
    override fun onResume(){
        super.onResume()
        var auth = Firebase.auth
        val user = auth.currentUser
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        var imgView=headerView.findViewById<ImageView>(R.id.photoUser)
        if(verify_logged_user()){
            navView.menu.findItem(R.id.nav_login).isVisible = false
            navView.menu.findItem(R.id.nav_logout).isVisible = true
            if (user != null) {
                headerView.findViewById<TextView>(R.id.UserTextView).text = user.displayName
                headerView.findViewById<TextView>(R.id.emailTextView).text = user.email
                headerView.findViewById<ImageView>(R.id.photoUser).setImageURI(user.photoUrl)

            }
        }else{
            navView.menu.findItem(R.id.nav_login).isVisible = true
            navView.menu.findItem(R.id.nav_logout).isVisible = false
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
//    override fun onRequestPermissionResult(
//        requestCode:Int,
//        permissions:Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
//        if(requestCode==111 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
//            headerView.findViewById<Button>(R.id.profilePicButton).isEnabled=true
//        }
//
//    }

}
fun verify_logged_user(): Boolean {

    var auth = Firebase.auth
    val user = auth.currentUser
    return user != null
}



