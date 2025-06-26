package com.jean.cuidemonosaqp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.work.HiltWorkerFactory
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jean.cuidemonosaqp.navigation.NavGraph
import com.jean.cuidemonosaqp.navigation.Routes
import com.jean.cuidemonosaqp.shared.components.BottomNavigationBar
import com.jean.cuidemonosaqp.shared.theme.CuidemonosAQPTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.app.Application
import androidx.work.Configuration
import com.jean.cuidemonosaqp.modules.auth.data.sync.SyncManager
import com.jean.cuidemonosaqp.shared.utils.ConnectionLiveData
import dagger.hilt.android.HiltAndroidApp
import android.widget.Toast


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var syncManager: SyncManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Programamos sincronización automática cada 15 minutos
        syncManager.syncPeriodically()

        // 2. Detectamos si hay conexión a internet con ConnectionLiveData
        val connectionLiveData = ConnectionLiveData(this)

        connectionLiveData.observe(this) { isConnected ->
            if (isConnected) {
                // 3. Si hay conexión, sincronizamos ahora y mostramos un Toast
                syncManager.syncNow()
                Toast.makeText(
                    this,
                    "✔ Datos sincronizados correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // 4. (Opcional) Si se pierde conexión, puedes mostrar otro mensaje
                Toast.makeText(
                    this,
                    "⚠ No hay conexión. Intentando luego...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        enableEdgeToEdge()
        setContent {
            CuidemonosAQPTheme(dynamicColor = false) {
                MainScreen()
            }
        }


    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Determinar si mostrar la barra de navegación
    val showBottomBar = when (currentRoute) {
        Routes.Auth.Login.route,
        Routes.Auth.Register.route -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


