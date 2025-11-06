package com.edgardev.buystudy.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.edgardev.buystudy.models.GastosList
import com.edgardev.buystudy.models.IngresoList
import com.edgardev.buystudy.models.TransaccionesList
import java.util.Calendar

class DBHelper(context: Context): SQLiteOpenHelper(context, BuyStudy, null, DATABASE_VERSION) {
    companion object {
        private const val BuyStudy = "App.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryNotas = ("CREATE TABLE NOTAS(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nota TEXT, " +
                "fecha TEXT)")
        db?.execSQL(queryNotas)

        val queryTransacciones = ("CREATE TABLE TRANSACCIONES(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cantidad TEXT, " +
                "tipo TEXT, " +
                "categoria TEXT, " +
                "fecha TEXT, " +
                "notas TEXT)")
        db?.execSQL(queryTransacciones)

        val queryPlanes = ("CREATE TABLE PLANES(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "plan TEXT, " +
                "precio TEXT)")
        db?.execSQL(queryPlanes)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS NOTAS"
        db?.execSQL(query)
        onCreate(db)

        val transacciones = "DROP TABLE IF EXISTS TRANSACCIONES"
        db?.execSQL(transacciones)
        onCreate(db)
    }

    // Insertar datos de nota
    fun insertarNota(nota: String, fecha: String): Long {
        val values = ContentValues().apply {
            put("nota", nota)
            put("fecha", fecha)
        }
        return writableDatabase.insert("NOTAS", null, values)
    }

    // Mostrar datos de la base de datos
    data class Nota(val id: Int, val titulo: String, val detalle: String, val fecha: String)
    fun obtenerTodasLasNotas(): List<Nota> {
        val notas = mutableListOf<Nota>()
        val db = readableDatabase
        val cursor = db.query("NOTAS", null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val titulo = cursor.getString(cursor.getColumnIndex("nota"))
            val fecha = cursor.getString(cursor.getColumnIndex("fecha"))

            // Puedes ajustar esto según la estructura real de tu tabla y la clase Nota
            val nota = Nota(id, titulo, "", fecha)
            notas.add(nota)
        }

        cursor.close()
        return notas
    }

    // Insertar datos de transacción
    fun insertarTransaccion(cantidad: String, tipo: String, categoria: String, fecha: String, notas: String): Long {
        val values = ContentValues().apply {
            put("cantidad", cantidad)
            put("tipo", tipo)
            put("categoria", categoria)
            put("fecha", fecha)
            put("notas", notas)
        }
        return writableDatabase.insert("TRANSACCIONES", null, values)
    }

    // insertar Plan
    fun insertarPlanCompra(objetoCompra: String, precio: String): Long {
        val values = ContentValues().apply {
            put("plan", objetoCompra)
            put("precio", precio)
        }
        return writableDatabase.insert("PLANES", null, values)
    }

    // Todos sus ingresos
    fun obtenerSumaIngresos(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM(cantidad) FROM TRANSACCIONES WHERE tipo = 'Ingresos'", null)
        var sumaIngresos = 0

        if (cursor.moveToFirst()) {
            sumaIngresos = cursor.getInt(0)
        }

        cursor.close()
        return sumaIngresos
    }

    // Todos sus gastos
    fun obtenerSumaGastos(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM(cantidad) FROM TRANSACCIONES WHERE tipo = 'Gastos'", null)
        var sumaGastos = 0

        if (cursor.moveToFirst()) {
            sumaGastos = cursor.getInt(0)
        }

        cursor.close()
        return sumaGastos
    }

    // Saldo Actual
    fun obtenerSaldoActual(): Int {
        val db = readableDatabase

        // Suma de ingresos
        val cursorIngresos = db.rawQuery("SELECT SUM(cantidad) FROM TRANSACCIONES WHERE tipo = 'Ingresos' Or tipo = 'Incomes'", null)
        var sumaIngresos = 0

        if (cursorIngresos.moveToFirst()) {
            sumaIngresos = cursorIngresos.getInt(0)
        }

        cursorIngresos.close()

        // Suma de gastos
        val cursorGastos = db.rawQuery("SELECT SUM(cantidad) FROM TRANSACCIONES WHERE tipo = 'Gastos' Or tipo = 'Expensis'", null)
        var sumaGastos = 0

        if (cursorGastos.moveToFirst()) {
            sumaGastos = cursorGastos.getInt(0)
        }

        cursorGastos.close()
        // Calcular el saldo actual
        val saldoActual = sumaIngresos - sumaGastos
        return saldoActual
    }

    // Obtener la suma de gastos del mes actual
    fun obtenerSumaGastosMesActual(): Int {
        val db = readableDatabase
        val currentDate = Calendar.getInstance()
        val currentMonth = String.format("%02d", currentDate.get(Calendar.MONTH) + 1)
        val currentYear = currentDate.get(Calendar.YEAR).toString()

        val cursor: Cursor = db.rawQuery(
            "SELECT SUM(cantidad) FROM TRANSACCIONES " +
                    "WHERE tipo = 'Gastos' or tipo = 'Expenses' AND substr(fecha, 4, 2) = ? AND substr(fecha, 7, 4) = ?",
            arrayOf(currentMonth, currentYear)
        )

        var sumaGastos = 0
        if (cursor.moveToFirst()) {
            sumaGastos = cursor.getInt(0)
        }

        cursor.close()
        return sumaGastos
    }


    // utimos gastos
    fun obtenerUltimas10Transacciones(): List<TransaccionesList> {
        val transacciones = mutableListOf<TransaccionesList>()
        val db = readableDatabase

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM TRANSACCIONES WHERE tipo = 'Gastos' Or tipo = 'Expenses' ORDER BY fecha DESC LIMIT 10",
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val cantidad = cursor.getString(cursor.getColumnIndex("cantidad"))
                val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
                val categoria = cursor.getString(cursor.getColumnIndex("categoria"))
                val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
                val notas = cursor.getString(cursor.getColumnIndex("notas"))

                val transaccion = TransaccionesList(id, cantidad, tipo, categoria, fecha, notas)
                transacciones.add(transaccion)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return transacciones
    }

    // Obtener los ingreso para mostrar en el cardview
    fun obtenerIngresos(): List<IngresoList> {
        val ingresosList = mutableListOf<IngresoList>()

        val db = this.readableDatabase
        val query = "SELECT * FROM TRANSACCIONES WHERE tipo = 'Ingresos' OR tipo = 'Incomes'"
        val cursor = db.rawQuery(query, null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val titulo = cursor.getString(cursor.getColumnIndex("categoria"))
                    val cantidad = cursor.getString(cursor.getColumnIndex("cantidad"))
                    val fecha = cursor.getString(cursor.getColumnIndex("fecha"))

                    val ingreso = IngresoList(id, titulo, cantidad, fecha)
                    ingresosList.add(ingreso)
                } while (cursor.moveToNext())
            }
        } finally {
            cursor.close()
            db.close()
        }

        return ingresosList
    }

    //recyclerView de gastos en history
    fun obtenerGastos(): List<GastosList> {
        val gastosList = mutableListOf<GastosList>()
        val db = this.readableDatabase
        val query = "SELECT * FROM TRANSACCIONES WHERE tipo = 'Gastos' OR tipo = 'Expenses'"
        val cursor = db.rawQuery(query, null)
        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val cantidad = cursor.getString(cursor.getColumnIndex("cantidad"))
                val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
                val categoria = cursor.getString(cursor.getColumnIndex("categoria"))
                val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
                val notas = cursor.getString(cursor.getColumnIndex("notas"))
                val gasto = GastosList(id, cantidad, tipo, categoria, fecha, notas)
                gastosList.add(gasto)
            }
        }
        cursor.close()
        return gastosList
    }
}