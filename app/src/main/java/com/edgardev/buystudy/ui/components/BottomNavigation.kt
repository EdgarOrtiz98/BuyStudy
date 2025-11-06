package com.edgardev.buystudy.ui.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.edgardev.buystudy.db.DBHelper
//import com.edgardev.buystudy.ui.AgendaScreen
//import com.edgardev.buystudy.ui.HistorialScreen
//import com.edgardev.buystudy.ui.InicioScreen
import com.edgardev.buystudy.ui.TransaccionesScreen
import com.edgardev.buystudy.viewmodel.TransaccionesViewModel

//modelo para cada item del BottomNavigation
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

class BottomNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("inicio", "Inicio", Icons.Default.Home),
        BottomNavItem("historial", "Historial", Icons.Default.List),
        BottomNavItem("agenda", "Agenda", Icons.Default.DateRange),
        BottomNavItem("transacciones", "Transacciones", Icons.Default.ShoppingCart)
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, items) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            //cambiar despues inicio. agenda  e historial a su respectivo composable
            composable("inicio") {
                val context = LocalContext.current
                val viewModel = remember { TransaccionesViewModel(DBHelper(context)) }
                TransaccionesScreen(viewModel = viewModel)
            }
            composable("historial") {
                val context = LocalContext.current
                val viewModel = remember { TransaccionesViewModel(DBHelper(context)) }
                TransaccionesScreen(viewModel = viewModel)
            }
            composable("agenda") {
                val context = LocalContext.current
                val viewModel = remember { TransaccionesViewModel(DBHelper(context)) }
                TransaccionesScreen(viewModel = viewModel)
            }
            composable("transacciones") {
                val context = LocalContext.current
                val viewModel = remember { TransaccionesViewModel(DBHelper(context)) }
                TransaccionesScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        //evita duplicados en el backstack
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
