package com.edgardev.buystudy

import DBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.edgardev.buystudy.databinding.FragmentAgendaBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import android.text.format.DateFormat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AgendaFragment : Fragment() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: FragmentAgendaBinding
    private lateinit var recyclerviewGastos: RecyclerView

    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(cal.time)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DBHelper(requireContext())

        Toast.makeText(requireContext(), "onCreate", Toast.LENGTH_SHORT).show()
        recyclerviewGastos = view.findViewById(R.id.recyclerviewNotas)
        recyclerviewGastos.layoutManager = LinearLayoutManager(requireContext())

        // Obtener datos de la base de datos
        val listaNotas = databaseHelper.obtenerTodasLasNotas()

        // Configurar el RecyclerView con los datos
        val adaptadorNotas = MyAdapter(listaNotas)
        recyclerviewGastos.adapter = adaptadorNotas


        binding.btnAgregarNotas.setOnClickListener {
            val builder = AlertDialog.Builder(view.context)
            val formNota = LayoutInflater.from(view.context).inflate(R.layout.cardview_form_notas, null)

            builder.setView(formNota)

            val dialog = builder.create()
            dialog.show()

            val editTextNota = formNota.findViewById<EditText>(R.id.editTextNota)
            val editTextFecha = formNota.findViewById<EditText>(R.id.editTextFechaNota)
            val btnGuardarNota = formNota.findViewById<Button>(R.id.btnGuardarNota)
            val btnCerrarNota = formNota.findViewById<ImageView>(R.id.cerrarFormNota)

            // Set the current date when the dialog is created
            editTextFecha.setText(getCurrentDate())

            btnCerrarNota.setOnClickListener {
                dialog.dismiss()
            }

            btnGuardarNota.setOnClickListener {
                val nota = editTextNota.text.toString().trim()
                val fecha = editTextFecha.text.toString().trim()

                if (nota.isNotEmpty() && fecha.isNotEmpty()) {
                    // Insertar en la base de datos
                    val dbHelper = DBHelper(requireContext())
                    val resultado = dbHelper.insertarNota(nota, fecha)

                    if (resultado != -1L) {
                        // Éxito
                        Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT)
                            .show()
                        dialog.dismiss()

                        // Obtener datos de la base de datos nuevamente
                        val nuevaListaNotas = databaseHelper.obtenerTodasLasNotas()

                        // Actualizar la lista en el adaptador
                        adaptadorNotas.actualizarLista(nuevaListaNotas)
                    } else {
                        // Error al insertar
                        val alerta = formNota.findViewById<TextView>(R.id.alertaNota)
                        alerta.text = "Error al registrar Nota"
                        alerta.visibility = View.VISIBLE
                    }
                } else {
                    // Campos vacíos
                    val alerta = formNota.findViewById<TextView>(R.id.alertaNota)
                    alerta.text = "Completa todos los campos"
                    alerta.visibility = View.VISIBLE
                }
            }
        }




    }


    override fun onStart() {
        super.onStart()

        Toast.makeText(requireContext(), "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(requireContext(), "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(requireContext(), "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(requireContext(), "onStop", Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(requireContext(), "onDestroy", Toast.LENGTH_SHORT).show()
    }
}
