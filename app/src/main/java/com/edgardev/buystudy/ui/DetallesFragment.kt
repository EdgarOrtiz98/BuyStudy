package com.edgardev.buystudy.ui

import com.edgardev.buystudy.db.DBHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgardev.buystudy.databinding.FragmentTransaccionesBinding

class DetallesFragment : Fragment() {

    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: FragmentTransaccionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransaccionesBinding.inflate(inflater, container, false)
        val view = binding.root

        databaseHelper = DBHelper(requireContext())

        return view
    }
}
