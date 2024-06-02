package com.edgardev.buystudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edgardev.buystudy.TransaccionesList

class AdapterUltimosGastos(private val transacciones: List<TransaccionesList>) :
    RecyclerView.Adapter<AdapterUltimosGastos.TransaccionViewHolder>() {

    inner class TransaccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById<TextView>(R.id.item_titulo)
        val detalle: TextView = itemView.findViewById(R.id.item_detalle)
        val fecha: TextView = itemView.findViewById(R.id.item_fecha)
        val id: TextView = itemView.findViewById(R.id.item_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaccionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_gasto, parent, false)
        return TransaccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransaccionViewHolder, position: Int) {
        val transaccion = transacciones[position]

        // Configurar los datos en la vista
        holder.titulo.text = transaccion.categoria
        holder.detalle.text = "- $ ${transaccion.cantidad}"
        holder.fecha.text = transaccion.fecha
        holder.id.text = transaccion.id.toString()

        // Manejar el clic en la transacción (puedes implementar la navegación a otro fragmento aquí)
        holder.itemView.setOnClickListener {
            // Implementa la lógica para manejar el clic aquí
        }
    }

    override fun getItemCount(): Int {
        return transacciones.size
    }
}
