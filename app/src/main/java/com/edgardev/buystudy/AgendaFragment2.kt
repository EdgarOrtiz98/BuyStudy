package com.edgardev.buystudy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgardev.buystudy.adapter.RvNotas
import com.edgardev.buystudy.databinding.FragmentAgenda2Binding
import com.edgardev.buystudy.models.Notas
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AgendaFragment2 : Fragment() {
    private var _binding : FragmentAgenda2Binding? = null
    private val binding get()= _binding!!

    private lateinit var NotesList: ArrayList<Notas>
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAgenda2Binding.inflate(inflater, container, false)

        binding.btnAgregarNotas.setOnClickListener {
            val fragment = FormNotesFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null) // Para agregar el Fragmento al stack de retroceso
            transaction.commit()
        }

        firebaseRef = FirebaseDatabase.getInstance().getReference("Notas")
        NotesList = arrayListOf()

        fetchDtata()

        binding.recyclerviewNotas.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this.context)
        }

        return binding.root
    }

    private fun fetchDtata() {
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                NotesList.clear()
                if (snapshot.exists()){
                    for (notaSnap in snapshot.children){
                        val notas = notaSnap.getValue(Notas::class.java)
                        NotesList.add(notas!!)
                    }
                }
                val RvAdapter = RvNotas(NotesList)
                binding.recyclerviewNotas.adapter = RvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error : ${error}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
