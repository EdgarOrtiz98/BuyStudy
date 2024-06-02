package com.edgardev.buystudy

import DBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.edgardev.buystudy.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DBHelper(requireContext())

        // Obtener el saldo actual
        val saldoActual = databaseHelper.obtenerSaldoActual()

        // Mostrar el saldo actual en el TextView
        binding.mostrarSaldo.text = "$ $saldoActual"

        // Obtener la suma de gastos del mes y año actual
        val sumaGastosMesActual = databaseHelper.obtenerSumaGastosMesActual()

        // Mostrar la suma de gastos en el TextView
        binding.mostrarGastosMes.text = "$ $sumaGastosMesActual"

        // Configurar el RecyclerView y su adaptador
        val recyclerView = binding.recyclerviewUltimosGasto
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val ultimasTransacciones = databaseHelper.obtenerUltimas10Transacciones()
        val adapter = AdapterUltimosGastos(ultimasTransacciones)
        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}