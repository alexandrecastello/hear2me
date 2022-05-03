package com.lft.hear4me.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lft.hear4me.HomeActivity
import com.lft.hear4me.databinding.FragmentLogoutBinding
import com.lft.hear4me.ui.audio.LogoutViewModel

class LogoutFragment : Fragment() {

    private var _binding: FragmentLogoutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var auth = Firebase.auth
    val user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val logoutViewModel =
            ViewModelProvider(this).get(LogoutViewModel::class.java)

        _binding = FragmentLogoutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.logoutConfirmationTextView
        logoutViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val buttonLogout: Button = binding.buttonLogout

        buttonLogout.setOnClickListener(){
            textView.text = "Você será encaminhado para a página inicial após o logout, aguarde."
            auth.signOut()
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}