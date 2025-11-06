package com.edgardev.buystudy.ui


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.edgardev.buystudy.R
import com.google.firebase.auth.FirebaseAuth
import com.edgardev.buystudy.models.ProviderType
import com.edgardev.buystudy.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onLoginSuccess: (String, ProviderType) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val showErrorDialog by viewModel.error.collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (imageTop, fondo, logo, title, form) = createRefs()

            //aqui se muestra el fondo degradado en lugar del shape drawable
            Box(
                modifier = Modifier
                    .height(320.dp)
                    .fillMaxWidth()
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
                    .constrainAs(imageTop) {
                        top.linkTo(parent.top)
                    }
            )

            //aqui se coloca el logo de la app
            Image(
                painter = painterResource(id = R.drawable.buystudy),
                contentDescription = null,
                modifier = Modifier.constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            //aqui se coloca el texto BuyStudy
            Text(
                text = "BuyStudy",
                color = Color.White,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 180.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .width(300.dp),
                textAlign = TextAlign.Center
            )

            //aqui se coloca el formulario
            Column(
                modifier = Modifier
                    .constrainAs(form) {
                        top.linkTo(parent.top, margin = 250.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.bienvenidos),
                    color = Color(0xFF384FBD),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                //campo de correo
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text(stringResource(R.string.correo)) },
                    modifier = Modifier.width(300.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(25.dp))

                //campo de contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = { Text(stringResource(R.string.contraseña)) },
                    modifier = Modifier.width(300.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(25.dp))

                //aqui esta el consumo de api
                //aqui se realiza la peticion post y se maneja el try-catch
                OutlinedButton(
                    onClick = {
                        try {
                            //aqui en el try se llama a FirebaseAuth para login
                            //si es exitoso, se obtiene el email y se navega
                            //si falla, se activa el diálogo de error genérico
                            FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userEmail = task.result?.user?.email ?: ""
                                        onLoginSuccess(userEmail, ProviderType.BASIC)
                                    } else {
                                        viewModel.setError(true)
                                    }
                                }
                        } catch (e: Exception) {
                            //aqui en el catch se evita mostrar detalles técnicos
                            viewModel.setError(true)
                        }
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp),
                    border = BorderStroke(3.dp, Color(0xFF384FBD))
                ) {
                    Text(
                        text = stringResource(R.string.acceder),
                        color = Color(0xFF384FBD),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                //aqui esta el consumo de api
                //aqui se realiza la peticion post para registrar y se maneja el try-catch
                Button(
                    onClick = {
                        try {
                            //aqui en el try se llama a FirebaseAuth para crear usuario
                            //si es exitoso, se limpian los campos y se muestra toast
                            //si falla, se activa el diálogo de error genérico
                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        viewModel.onEmailChange("")
                                        viewModel.onPasswordChange("")
                                        Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                    } else {
                                        viewModel.setError(true)
                                    }
                                }
                        } catch (e: Exception) {
                            //aqui en el catch se evita mostrar detalles técnicos
                            viewModel.setError(true)
                        }
                    },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF384FBD))
                ) {
                    Text(
                        text = stringResource(R.string.registrarse),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        //aqui se muestra el diálogo genérico de error
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.setError(false)
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.setError(false) }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Error") },
                text = { Text("Se ha producido un error autenticando al usuario") }
            )
        }
    }
}