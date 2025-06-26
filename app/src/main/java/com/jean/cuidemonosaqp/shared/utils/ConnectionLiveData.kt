package com.jean.cuidemonosaqp.shared.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

/**
 * LiveData que detecta si el dispositivo tiene conexión a Internet.
 * Emite true cuando hay conexión, false cuando no.
 */
class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    // Obtenemos el servicio del sistema para manejar la conectividad
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Callback que se activa cuando cambia la conectividad
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        // Cuando la red está disponible (por ejemplo, WiFi o datos móviles)
        override fun onAvailable(network: Network) {
            postValue(true) // Emitimos true: hay conexión
        }

        // Cuando se pierde la conexión
        override fun onLost(network: Network) {
            postValue(false) // Emitimos false: no hay conexión
        }
    }

    // Cuando alguien empieza a observar esta LiveData
    override fun onActive() {
        super.onActive()
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    // Cuando ya no hay observadores activos
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}