package com.edgardev.buystudy.ui

import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edgardev.buystudy.R
import com.edgardev.buystudy.ui.components.BottomLineDatePicker
import com.edgardev.buystudy.ui.components.BottomLineDropdown
import com.edgardev.buystudy.ui.components.BottomLineTextField
import com.edgardev.buystudy.viewmodel.TransaccionesViewModel
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TransaccionesScreen(viewModel: TransaccionesViewModel) {
    val tipos = listOf("Gastos", "Ingresos")
    val categoriasGastos = listOf("Comida", "Transporte", "Otros")
    val categoriasIngresos = listOf("Salario", "Regalo", "Otros")

    //colores
    val white = Color(0xFFFFFFFF)
    val black = Color(0xFF000000)
    val buttonColor = Color(0xFF384FBD)
    val successColor = Color(0xFF0D7F0C)
    val errorColor = Color(0xFFFF0000)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
            .verticalScroll(rememberScrollState())
    ) {
        //header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .offset(y = (-25).dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF2b6cca), //startColor
                            Color(0xFF264b82), //centerColor
                            Color(0xFF264b82)  //endColor
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(200f, 200f) //ángulo aproximado -45°
                    ),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(20.dp)
        ) {
            //contenido sobre la imagen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 20.dp)
            ) {
                //título "Transacciones"
                Text(
                    text = "Transacciones",
                    color = white,
                    fontSize = 23.sp,
                    modifier = Modifier.padding(bottom = 25.dp)
                )

                //sección de cantidad con formato similar al XML
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = "$",
                        color = white,
                        fontSize = 23.sp,
                        modifier = Modifier.padding(end = 10.dp)
                    )

                    OutlinedTextField(
                        value = viewModel.cantidad,
                        onValueChange = { viewModel.cantidad = it },
                        placeholder = {
                            Text(
                                "0",
                                color = white.copy(alpha = 0.7f),
                                fontSize = 40.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .offset(y = (-5).dp),
                        textStyle = LocalTextStyle.current.copy(
                            color = white,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedTextColor = white,
                            focusedTextColor = white,
                            cursorColor = white,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }

        //formulario principal
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(top = 55.dp) // Margen desde el header
        ) {
            //tipo (Dropdown)
            Text(
                text = "Tipos",
                color = black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            BottomLineDropdown(
                label = "Tipo",
                options = tipos,
                selectedOption = viewModel.tipo,
                onOptionSelected = { viewModel.tipo = it }
            )

            Spacer(Modifier.height(15.dp))

            //categoría
            Text(
                text = "Categoría",
                color = black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            BottomLineDropdown(
                label = "Categoría",
                options = if (viewModel.tipo == "Gastos") categoriasGastos else categoriasIngresos,
                selectedOption = viewModel.categoria,
                onOptionSelected = { viewModel.categoria = it }
            )

            Spacer(Modifier.height(15.dp))

            //fecha (DatePickerDialog)
            Text(
                text = "Fecha",
                color = black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            BottomLineDatePicker(
                label = "Fecha",
                value = viewModel.fecha,
                onDateSelected = { viewModel.fecha = it }
            )

            Spacer(Modifier.height(15.dp))

            // Notas
            Text(
                text = "Notas",
                color = black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            BottomLineTextField(
                label = "Notas",
                value = viewModel.notas,
                onValueChange = { viewModel.notas = it }
            )

            Spacer(Modifier.height(25.dp))

            if (viewModel.mensajeExito.isNotEmpty()) {
                Text(
                    text = viewModel.mensajeExito,
                    color = successColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )
            }

            if (viewModel.mensajeError.isNotEmpty()) {
                Text(
                    text = viewModel.mensajeError,
                    color = errorColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(bottom = 10.dp)
                )
            }

            // Botón Confirmar
            Button(
                onClick = { viewModel.registrarTransaccion() },
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                )
            ) {
                Text(
                    text = "Confirmar",
                    color = white,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}