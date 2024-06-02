package com.edgardev.buystudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.edgardev.buystudy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("dato","On create")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setup()
    }

    override fun onStart() {
        super.onStart()
        Log.i("dato","On start")
    }

    override fun onResume() {
        super.onResume()
        Log.i("dato","On resume")

    }

    override fun onPause() {
        super.onPause()
        Log.i("dato","On pause")

    }

    override fun onStop() {
        super.onStop()
        Log.i("dato","On stop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("dato","On destroy")

    }



    private fun setup() {
        binding.btnAccederLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showHome(task.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.btnRegistrarLogin.setOnClickListener {
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            binding.emailLogin.text.clear()
                            binding.passwordLogin.text.clear()
                            Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_SHORT).show()
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, BottomNavigation::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish() // Cierra la actividad actual
        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
    }
}
