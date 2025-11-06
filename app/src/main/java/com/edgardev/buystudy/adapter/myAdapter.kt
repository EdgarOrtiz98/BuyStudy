package com.edgardev.buystudy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edgardev.buystudy.R
import com.edgardev.buystudy.db.DBHelper

class MyAdapter(private var listaNotas: List<DBHelper.Nota>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_general, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNota = listaNotas[position]
        holder.bind(currentNota)
    }

    override fun getItemCount(): Int {
        return listaNotas.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.item_titulo)
        private val detalleTextView: TextView = itemView.findViewById(R.id.item_detalle)
        private val fechaTextView: TextView = itemView.findViewById(R.id.item_fecha)
        private val idTextView: TextView = itemView.findViewById(R.id.item_id)

        fun bind(nota: DBHelper.Nota) {
            tituloTextView.text = nota.titulo
            detalleTextView.text = nota.detalle
            fechaTextView.text = nota.fecha
            idTextView.text = nota.id.toString()
        }
    }

    fun actualizarLista(nuevaLista: List<DBHelper.Nota>) {
        listaNotas = nuevaLista
        notifyDataSetChanged()
    }
}

