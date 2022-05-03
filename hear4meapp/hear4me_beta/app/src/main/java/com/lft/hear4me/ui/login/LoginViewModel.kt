package com.lft.hear4me.ui.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lft.hear4me.HomeActivity
import com.lft.hear4me.R
import com.lft.hear4me.data.login.model.LoggedInUser

class LoginViewModel() : ViewModel() {

        private val _loginForm = MutableLiveData<LoginFormState>()
        val loginFormState: LiveData<LoginFormState> = _loginForm

        private val _loginResult = MutableLiveData<LoginResult>()
        val loginResult: LiveData<LoginResult> = _loginResult

        fun login(username: String, password: String) {
                val TAG = "EmailPassword"
                lateinit var userInfo: LoggedInUser
                var auth: FirebaseAuth?
                var flag: Boolean
                auth = Firebase.auth

                auth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        Log.d(TAG, "signInWithEmail:success")
                                        val user = auth.currentUser
                                        if (user!!.isEmailVerified()) {
                                                _loginResult.value =
                                                        LoginResult(success = LoggedInUserView(displayName =username))
                                                UpdateUI(username,user)
                                        } else {
                                                _loginResult.value =
                                                        LoginResult(error = R.string.user_not_verified)
                                        }
                                } else {
                                        // If sign in fails, display a message to the user.
                                        val exception: Exception? = task.exception
                                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                                        userInfo = LoggedInUser("error", "error")
                                        _loginResult.value = LoginResult(error = R.string.login_failed)
                                }
                        }
        }

        fun UpdateUI(username:String,user: FirebaseUser){

        }

        fun loginDataChanged(username: String, password: String) {
                if (!isUserNameValid(username)) {
                        _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
                } else if (!isPasswordValid(password)) {
                        _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
                } else {
                        _loginForm.value = LoginFormState(isDataValid = true)
                }
        }

        // A placeholder username validation check
        private fun isUserNameValid(username: String): Boolean {
                return Patterns.EMAIL_ADDRESS.matcher(username).matches()


        }

        // A placeholder password validation check
        private fun isPasswordValid(password: String): Boolean {
                return password.length > 5 && password.lowercase()!=password && password.filter{it.isLetterOrDigit()}!=password
        }
}