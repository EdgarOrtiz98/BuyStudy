package com.edgardev.buystudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.edgardev.buystudy.models.ProviderType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    // Estado expuesto a la UI
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun setError(show: Boolean) {
        _error.value = show
    }

    fun login(onSuccess: (String, ProviderType) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userEmail = task.result?.user?.email ?: ""
                            onSuccess(userEmail, ProviderType.BASIC)
                        } else {
                            _error.value = true
                        }
                    }
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _email.value = ""
                            _password.value = ""
                            onSuccess()
                        } else {
                            _error.value = true
                        }
                    }
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }
}
