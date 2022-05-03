package com.lft.hear4me.ui.register

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val passwordConfError: Int? = null,
    val TermoError: Int? = null,
    val isDataValid: Boolean = false
)