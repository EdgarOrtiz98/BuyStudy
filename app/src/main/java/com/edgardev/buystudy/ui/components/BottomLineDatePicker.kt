package com.edgardev.buystudy.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomLineDatePicker(
    label: String,
    value: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth()) {

        TextField(
            value = value,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            placeholder = { Text(text = "Seleccionar fecha", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    val currentDate = Calendar.getInstance()
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val cal = Calendar.getInstance()
                            cal.set(year, month, day)
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            onDateSelected(dateFormat.format(cal.time))
                        },
                        currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH),
                        currentDate.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
        )
    }
}
