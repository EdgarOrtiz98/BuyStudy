package com.edgardev.buystudy.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.edgardev.buystudy.models.GastosList
import com.edgardev.buystudy.R

class AdapterGastos(private val gastosList: List<GastosList>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<AdapterGastos.GastosViewHolder>() {

    inner class GastosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val titulo: TextView = itemView.findViewById(R.id.item_titulo)
        val detalle: TextView = itemView.findViewById(R.id.item_detalle)
        val fecha: TextView = itemView.findViewById(R.id.item_fecha)
        val id: TextView = itemView.findViewById(R.id.item_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastosViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_gasto, parent, false)
        return GastosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GastosViewHolder, position: Int) {
        val currentItem = gastosList[position]
        holder.titulo.text = currentItem.categoria
        holder.detalle.text = "- $ ${currentItem.cantidad}"
        holder.fecha.text = currentItem.fecha
        holder.id.text = currentItem.id.toString()

        holder.cardView.setOnClickListener {
            onItemClick(currentItem.id)
        }
    }

    override fun getItemCount(): Int {
        return gastosList.size
    }
}
