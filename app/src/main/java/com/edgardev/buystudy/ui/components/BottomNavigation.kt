package com.edgardev.buystudy.ui.components

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.edgardev.buystudy.R
import com.edgardev.buystudy.databinding.ActivityMainBinding
import com.edgardev.buystudy.ui.AgendaFragment2
import com.edgardev.buystudy.ui.HistorialFragment
import com.edgardev.buystudy.ui.InicioFragment
import com.edgardev.buystudy.ui.TransaccionesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

enum class ProviderType {
    BASIC
}

class BottomNavigation : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    // private lateinit var btnCerrarSesion: Button
    // private lateinit var emailCerrar: TextView
    // private lateinit var providerCerrar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)


        // binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(binding.root)

        // emailCerrar = findViewById(R.id.emailTextView)
        // providerCerrar = findViewById(R.id.providerTextView)
        // btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        // val bundle = intent.extras
        // val email = bundle?.getString("email")
        // val provider = bundle?.getString("provider")
        // setUp(email ?: "", provider ?: "")

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.inicio -> {
                    replaceFragment(InicioFragment())
                    true
                }
                R.id.historial -> {
                    replaceFragment(HistorialFragment())
                    true
                }
                R.id.agenda -> {
                    replaceFragment(AgendaFragment2())
                    true
                }
                R.id.transaccion -> {
                    replaceFragment(TransaccionesFragment())
                    true
                }
                else -> false
            }
        }

        replaceFragment(InicioFragment())
    }

    //private fun setUp(email: String, provider: String) {
    //    emailCerrar.text = email
    //    providerCerrar.text = provider
//
    //    btnCerrarSesion.setOnClickListener {
    //        FirebaseAuth.getInstance().signOut()
    //        val intent = Intent(this, MainActivity::class.java)
    //        startActivity(intent)
    //    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}