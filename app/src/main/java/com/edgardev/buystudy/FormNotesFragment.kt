package com.edgardev.buystudy

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edgardev.buystudy.databinding.FragmentFormNotesBinding
import com.edgardev.buystudy.models.Notas
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class FormNotesFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding : FragmentFormNotesBinding? = null
    private val binding get()= _binding!!

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormNotesBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("Notas")

        binding.FechaEditText.setOnClickListener {
            showDatePicker()
        }

        binding.btnComfirmarNota.setOnClickListener {
            saveData()
        }

        return binding.root
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedCalendar = Calendar.getInstance()
        selectedCalendar.set(Calendar.YEAR, year)
        selectedCalendar.set(Calendar.MONTH, month)
        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateString = dateFormat.format(selectedCalendar.time)

        binding.FechaEditText.setText(dateString)
    }

    private fun saveData() {
        val nota = binding.NotaEditText.text.toString()
        val fecha = binding.FechaEditText.text.toString()

        if (nota.isEmpty()) {
            binding.NotaEditText.error = "Escribe una Nota"
            return
        }

        if (fecha.isEmpty()) {
            binding.FechaEditText.error = "Selecciona una Fecha"
            return
        }

        val notaid = firebaseRef.push().key!!
        val notes = Notas(notaid, nota, fecha)

        firebaseRef.child(notaid).setValue(notes)
            .addOnCompleteListener {
                Toast.makeText(context, "Datos Registrados Exitosamente", Toast.LENGTH_SHORT).show()
                binding.NotaEditText.text.clear()
                binding.FechaEditText.text.clear()
                val fragment = AgendaFragment2()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
