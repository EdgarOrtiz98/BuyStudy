package com.edgardev.buystudy.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.edgardev.buystudy.models.ProviderType
import com.edgardev.buystudy.ui.components.BottomNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                onLoginSuccess = { email, provider ->
                    showHome(email, provider)
                }
            )
        }
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, BottomNavigation::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
        finish()
        Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show()
    }
}