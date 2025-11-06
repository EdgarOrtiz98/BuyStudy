package com.edgardev.buystudy.ui

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edgardev.buystudy.databinding.FragmentDetallesListBinding
import com.edgardev.buystudy.db.DBHelper

class DetallesListFragment : Fragment() {

    private lateinit var binding: FragmentDetallesListBinding
    private lateinit var databaseHelper: DBHelper
    private var transactionId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DBHelper(requireContext())

        // Obtener el ID de la transacción desde los argumentos
        arguments?.let {
            transactionId = it.getInt("transactionId")
        }

        // Consultar la base de datos para obtener los detalles de la transacción
        val transactionDetails = getTransactionDetails(transactionId)
        if (transactionDetails != null) {
            binding.txtCategoria.text = transactionDetails.getString(3)
            binding.txtCantidad.text = transactionDetails.getString(1)
            binding.txtFechaValor.text = transactionDetails.getString(4)
        }
    }

    private fun getTransactionDetails(id: Int): Cursor? {
        return databaseHelper.readableDatabase.rawQuery("SELECT * FROM TRANSACCIONES WHERE id = ?", arrayOf(id.toString()))
    }
}
