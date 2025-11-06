package com.edgardev.buystudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.edgardev.buystudy.ui.AgendaFragment2Directions
import com.edgardev.buystudy.databinding.CardviewGeneralBinding
import com.edgardev.buystudy.models.Notas
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase

class RvNotas(private val NotesList : java.util.ArrayList<Notas>) : RecyclerView.Adapter<RvNotas.ViewHolder>() {
    class ViewHolder(val binding : CardviewGeneralBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardviewGeneralBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = NotesList[position]
        holder.apply {
            binding.apply {
                itemTitulo.text = currentItem.nota
                itemFecha.text = currentItem.fecha
                itemId.text = currentItem.id

                // RvContainer.setOnClickListener {
                //     val action = AgendaFragment2Directions.actionAgendaFragment2ToEditNotaFragment(
                //         currentItem.id.toString(),
                //         currentItem.nota.toString(),
                //         currentItem.fecha.toString()
                //     )
                //     findNavController(holder.itemView).navigate(action)
                // }

                itemDelete.setOnClickListener{
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Delete item permanently")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes"){_,_->
                            val firebaseRef = FirebaseDatabase.getInstance().getReference("Notas")
                            firebaseRef.child(currentItem.id.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(holder.itemView.context, "Successfully removed", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{error ->
                                    Toast.makeText(holder.itemView.context, "Error ${error.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("No") { _, _ ->
                            Toast.makeText(holder.itemView.context, "cancelled", Toast.LENGTH_SHORT).show()
                        }
                        .show()

                    return@setOnClickListener
                }
            }
        }
    }
}