package com.edgardev.buystudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.edgardev.buystudy.db.DBHelper

class TransaccionesViewModel(private val dbHelper: DBHelper) : ViewModel() {
    //variables del form
    var cantidad by mutableStateOf("")
    var tipo by mutableStateOf("Gastos")
    var categoria by mutableStateOf("")
    var fecha by mutableStateOf("")
    var notas by mutableStateOf("")

    //variables para los "mensajes"
    var mensajeError by mutableStateOf("")
    var mensajeExito by mutableStateOf("")

    //funcion para registrar la transaccion
    fun registrarTransaccion() {
        if (cantidad.isEmpty() || fecha.isEmpty() || notas.isEmpty()) {
            mensajeError = "Todos los campos son requeridos"
            //Mensaje que estaba antes: "All fields are required"
            mensajeExito = ""
            return
        }

        val resultado = dbHelper.insertarTransaccion(cantidad, tipo, categoria, fecha, notas)
        if (resultado != -1L) {
            mensajeExito = "La transacción se ha registrado exitosamente"
            //Mensaje que estaba antes: "Transaction successfully registered"
            mensajeError = ""
            cantidad = "0"
            fecha = ""
            notas = ""
        } else {
            mensajeError = "Error al registrar la transacción"
            mensajeExito = ""
        }
    }
}