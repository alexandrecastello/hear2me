package com.lft.hear4me.ui.register

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.lft.hear4me.databinding.ActivityRegisterBinding
import com.lft.hear4me.R
import com.lft.hear4me.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val password2=binding.password2
        val termo=binding.confirmaTermos
        val comercial=binding.confirmaComercial
        val contato=binding.confirmaContato
        val displayName=binding.displayNamePlaceholder
        val register=binding.registro
        val loading = binding.loading

        val returnMain: Button = findViewById<Button>(R.id.retornaMain)

        val registerViewModel: RegisterViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)


        registerViewModel.registerFormState.observe(this@RegisterActivity, Observer {
            val registerState = it ?: return@Observer

            // disable register button unless both username / password is valid
            register.isEnabled = registerState.isDataValid

            if (registerState.usernameError != null) {
                username.error = getString(registerState.usernameError)
            }
            if (registerState.passwordError != null) {
                password.error = getString(registerState.passwordError)
            }
            if (registerState.passwordConfError != null) {
                password2.error = getString(registerState.passwordConfError)
            }
        })

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                showRegisterSuccess(registerResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy register activity once successful
            finish()
        })

        username.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                password2.text.toString(),
                termo
            )
        }
        password.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                password2.text.toString(),
                termo
            )
        }
        password2.afterTextChanged {
            registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                password2.text.toString(),
                termo
            )
        }
        termo.setOnCheckedChangeListener { buttonView, isChecked ->
                registerViewModel.registerDataChanged(
                username.text.toString(),
                password.text.toString(),
                password2.text.toString(),
                termo
                )
        }
        password2.apply {

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        registerViewModel.register(
                            username.text.toString(),
                            password.text.toString(),
                            displayName.text.toString()
                        )
                }
                false
            }
            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                registerViewModel.register(
                    username.text.toString(),
                    password.text.toString(),
                    displayName.text.toString()
                )
            }
        }

        returnMain.setOnClickListener {
            val intentResponse = Intent(this, LoginActivity::class.java)
            startActivity(intentResponse)
        }

    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun showRegisterSuccess(@StringRes successString: Int) {
        Toast.makeText(applicationContext, successString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}