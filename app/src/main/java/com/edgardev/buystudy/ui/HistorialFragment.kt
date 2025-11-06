package com.edgardev.buystudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgardev.buystudy.databinding.FragmentHistorialBinding
import com.edgardev.buystudy.db.DBHelper
import com.edgardev.buystudy.adapter.AdapterGastos
import com.edgardev.buystudy.adapter.AdapterIngresos
import com.edgardev.buystudy.models.GastosList

class HistorialFragment : Fragment() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: FragmentHistorialBinding
    private lateinit var adapter: AdapterGastos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DBHelper(requireContext())

        val gastosList = ArrayList<GastosList>()
        // Consulta a la base de datos y agregando resultados a la lista
        val cursor = databaseHelper.readableDatabase.rawQuery("SELECT * FROM TRANSACCIONES WHERE tipo = 'Gastos' OR tipo = 'Expenses'", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val cantidad = cursor.getString(cursor.getColumnIndex("cantidad"))
            val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
            val categoria = cursor.getString(cursor.getColumnIndex("categoria"))
            val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
            val notas = cursor.getString(cursor.getColumnIndex("notas"))

            val gasto = GastosList(id, cantidad, tipo, categoria, fecha, notas)
            gastosList.add(gasto)
        }
        cursor.close()

        adapter = AdapterGastos(gastosList) { gastoId ->
            // Aquí puedes manejar el clic del item, por ejemplo:
            Toast.makeText(requireContext(), "Clic en el gasto con ID: $gastoId", Toast.LENGTH_SHORT).show()
            // También puedes hacer la redirección al otro fragmento aquí
        }

        binding.recyclerviewGasto.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewGasto.adapter = adapter

        val sumaGastos = gastosList.sumBy { it.cantidad.toInt() }
        binding.cantidadgasto.text = "$ $sumaGastos"


        databaseHelper = DBHelper(requireContext())

        val ingresosList = databaseHelper.obtenerIngresos()
        val adapter = AdapterIngresos(ingresosList) { ingresoId ->
        }

        binding.recyclerviewIngresos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewIngresos.adapter = adapter

        val sumaIngresos = ingresosList.sumBy { it.cantidad.toInt() }
        binding.cantidadingreso.text = "$ $sumaIngresos"

    }
}
