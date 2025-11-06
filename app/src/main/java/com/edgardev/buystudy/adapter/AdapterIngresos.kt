package com.edgardev.buystudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edgardev.buystudy.models.IngresoList
import com.edgardev.buystudy.databinding.CardviewIngresoBinding

class AdapterIngresos(private val ingresos: List<IngresoList>, private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<AdapterIngresos.ViewHolder>() {

    inner class ViewHolder(private val binding: CardviewIngresoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingreso: IngresoList) {
            binding.itemTitulo.text = ingreso.titulo
            binding.itemDetalle.text = ingreso.cantidad
            binding.itemFecha.text = ingreso.fecha
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardviewIngresoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingresos[position])
    }

    override fun getItemCount(): Int {
        return ingresos.size
    }
}
