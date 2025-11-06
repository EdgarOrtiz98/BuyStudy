package com.edgardev.buystudy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.edgardev.buystudy.databinding.FragmentFormNotesBinding

class EditNotaFragment : Fragment() {
    private var _binding : FragmentFormNotesBinding? = null
    private val binding get()= _binding!!

    private val args : EditNotaFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormNotesBinding.inflate(inflater, container, false)

        binding.apply {
            NotaEditText.setText(args.nota)
            FechaEditText.setText(args.fecha)
            btnComfirmarNota
        }

        return binding.root
    }
}