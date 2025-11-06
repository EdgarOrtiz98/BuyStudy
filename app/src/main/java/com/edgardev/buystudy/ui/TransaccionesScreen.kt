package com.edgardev.buystudy.ui

import com.edgardev.buystudy.db.DBHelper
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.edgardev.buystudy.R
import com.edgardev.buystudy.databinding.FragmentTransaccionesBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
//importaciones para compose
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.edgardev.buystudy.viewmodel.TransaccionesViewModel

@Composable
fun TransaccionesScreen(viewModel: TransaccionesViewModel) {
    val tipos = listOf("Gastos", "Ingresos")
    val categoriasGastos = listOf("Comida", "Transporte", "Otros")
    val categoriasIngresos = listOf("Salario", "Regalo", "Otros")

    Column(modifier = Modifier.padding(16.dp)) {
        // Cantidad
        OutlinedTextField(
            value = viewModel.cantidad,
            onValueChange = { viewModel.cantidad = it },
            label = { Text("Cantidad") }
        )

        Spacer(Modifier.height(8.dp))

        // Tipo (Dropdown)
        var expandedTipo by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = viewModel.tipo,
                onValueChange = {},
                label = { Text("Tipo") },
                readOnly = true,
                modifier = Modifier.clickable { expandedTipo = true }
            )
            DropdownMenu(expanded = expandedTipo, onDismissRequest = { expandedTipo = false }) {
                tipos.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.tipo = tipo
                            expandedTipo = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Categoría según tipo
        val categorias = if (viewModel.tipo == "Gastos") categoriasGastos else categoriasIngresos
        var expandedCategoria by remember { mutableStateOf(false) }
        Box {
            OutlinedTextField(
                value = viewModel.categoria,
                onValueChange = {},
                label = { Text("Categoría") },
                readOnly = true,
                modifier = Modifier.clickable { expandedCategoria = true }
            )
            DropdownMenu(expanded = expandedCategoria, onDismissRequest = { expandedCategoria = false }) {
                categorias.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            viewModel.categoria = cat
                            expandedCategoria = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Fecha (DatePickerDialog)
        val context = LocalContext.current
        OutlinedTextField(
            value = viewModel.fecha,
            onValueChange = {},
            label = { Text("Fecha") },
            readOnly = true,
            modifier = Modifier.clickable {
                val currentDate = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(year, month, day)
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        viewModel.fecha = dateFormat.format(selectedDate.time)
                    },
                    currentDate.get(Calendar.YEAR),
                    currentDate.get(Calendar.MONTH),
                    currentDate.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        )

        Spacer(Modifier.height(8.dp))

        // Notas
        OutlinedTextField(
            value = viewModel.notas,
            onValueChange = { viewModel.notas = it },
            label = { Text("Notas") }
        )

        Spacer(Modifier.height(16.dp))

        // Botón
        Button(onClick = { viewModel.registrarTransaccion() }) {
            Text("Confirmar Transacción")
        }

        // Mensajes
        if (viewModel.mensajeError.isNotEmpty()) {
            Text(viewModel.mensajeError, color = Color.Red)
        }
        if (viewModel.mensajeExito.isNotEmpty()) {
            Text(viewModel.mensajeExito, color = Color.Green)
        }
    }
}