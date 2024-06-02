package com.edgardev.buystudy

import DBHelper
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edgardev.buystudy.databinding.FragmentTransaccionesBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransaccionesFragment : Fragment() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var editTextFecha: EditText
    private lateinit var binding: FragmentTransaccionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransaccionesBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseHelper = DBHelper(requireContext())

        editTextFecha = binding.editTextFechaTransaccion

        val spinnerType = binding.spinnerTipoTransaccion
        val spinnerCategory = binding.spinnerCategoryGastos
        val spinnerCategoryIngreso = binding.spinnerCategoryIngreso

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                val selectedType = parentView?.getItemAtPosition(position).toString()

                if (selectedType == "Gastos" || selectedType == "Expenses") {
                    spinnerCategory.visibility = View.VISIBLE
                    spinnerCategoryIngreso.visibility = View.GONE
                } else {
                    spinnerCategory.visibility = View.GONE
                    spinnerCategoryIngreso.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
        editTextFecha.setOnClickListener {
            showDatePicker()
        }
        return view
    }


    private fun showDatePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)

                // Formatea la fecha según tus necesidades
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)

                // Establece la fecha formateada en el EditText
                editTextFecha.setText(formattedDate)
            }, year,
            month,
            day
        )

        datePickerDialog.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnComfirmarTransaccion.setOnClickListener {
            // Obtener valores de los campos
            val cantidad = binding.editTextCantidadTransaccion.text.toString()
            val tipo = binding.spinnerTipoTransaccion.selectedItem.toString()
            val categoria = when (tipo) {
                "Gastos" -> binding.spinnerCategoryGastos.selectedItem.toString()
                else -> binding.spinnerCategoryIngreso.selectedItem.toString()
            }
            val fecha = binding.editTextFechaTransaccion.text.toString()
            val notas = binding.editTextNotaTrasaccion.text.toString()

            // Validar que los campos no estén vacíos
            if (cantidad.isEmpty() || fecha.isEmpty() || notas.isEmpty()) {
                // Mostrar mensaje de error
                binding.alertaTransaccion.text = "All fields are required"
                binding.alertaTransaccion.visibility = View.VISIBLE
                binding.alertaTransaccionExitosa.visibility = View.GONE
                return@setOnClickListener
            }

            // Insertar en la base de datos
            val dbHelper = DBHelper(requireContext())
            val resultado = dbHelper.insertarTransaccion(cantidad, tipo, categoria, fecha, notas)

            // Verificar si la inserción fue exitosa
            if (resultado != -1L) {
                // Éxito
                binding.alertaTransaccionExitosa.text = "Transaction successfully registered"
                binding.alertaTransaccionExitosa.visibility = View.VISIBLE
                binding.alertaTransaccion.visibility = View.GONE

                // Limpiar los campos
                binding.editTextCantidadTransaccion.setText("0")
                binding.editTextFechaTransaccion.text.clear()
                binding.editTextNotaTrasaccion.text.clear()
            } else {
                // Error al insertar
                binding.alertaTransaccion.text = "Error al registrar la transacción"
                binding.alertaTransaccion.visibility = View.VISIBLE
                binding.alertaTransaccionExitosa.visibility = View.GONE
            }
        }
    }
}
