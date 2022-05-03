package com.lft.hear4me.ui.register

import android.util.Log
import android.util.Patterns
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.lft.hear4me.R


class RegisterViewModel() : ViewModel() {

        private val _registerForm = MutableLiveData<RegisterFormState>()
        val registerFormState: LiveData<RegisterFormState> = _registerForm

        private val _registerResult = MutableLiveData<RegisterResult>()
        val registerResult: LiveData<RegisterResult> = _registerResult

        fun register(username: String, password: String,DisplayName:String) {
                val TAG = "EmailPassword"
                var auth: FirebaseAuth?
                auth = Firebase.auth
                auth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success")
                                        val firebaseUser = auth.currentUser
                                        val profileUpdates = userProfileChangeRequest {
                                                displayName = DisplayName
                                        }
                                        //send email verification
                                        firebaseUser!!.sendEmailVerification()
                                                .addOnSuccessListener {
                                                        _registerResult.value = RegisterResult(success = R.string.login_success)
                                                }
                                                .addOnFailureListener { e ->
                                                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                                        _registerResult.value = RegisterResult(error = R.string.already_reg)
                                                }
                                } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                        _registerResult.value = RegisterResult(error = R.string.register_failed)
                                }
                        }
        }

        fun registerDataChanged(username: String, password: String, password2:String, Termo:CheckBox) {
                if (!isUserNameValid(username)) {
                        _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
                } else if (!isPasswordValid(password)) {
                        _registerForm.value = RegisterFormState(passwordError = R.string.valid_password)
                } else if (!passwordValidation(password,password2)) {
                        _registerForm.value = RegisterFormState(passwordConfError = R.string.password_confirmation)
                } else if (!isCheckboxMarked(Termo)) {
                        _registerForm.value = RegisterFormState(TermoError= R.string.terms_and_conditions_error)
                } else {
                        _registerForm.value = RegisterFormState(isDataValid = true)
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

        // A placeholder password validation check
        private fun passwordValidation(password: String,password2: String): Boolean {
                return password==password2
        }

        // Checkbox Terms check
        private fun isCheckboxMarked(Termo: CheckBox): Boolean {
                return Termo.isChecked
        }




}