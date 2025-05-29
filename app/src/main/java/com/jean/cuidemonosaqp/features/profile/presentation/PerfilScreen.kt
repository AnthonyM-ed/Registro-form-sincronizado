package com.jean.cuidemonosaqp.features.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jean.cuidemonosaqp.R

@Composable
fun PerfilScreen(viewModel: PerfilViewModel = viewModel()) {

    val perfil by viewModel.perfilUiState.collectAsState()
    val resenas by viewModel.resenas.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Imagen de perfil con borde azul
        Box {
            Image(
                painter = painterResource(id = R.drawable.perfil_mujer),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            // Ícono de verificación
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3))
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre
        Text(
            text = perfil.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Miembro desde
        Text(
            text = "Miembro desde: ${perfil.miembroDesde}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Rating con estrellas
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(4) {
                Text("⭐", fontSize = 20.sp)
            }
            Text("☆", fontSize = 20.sp, color = Color.Gray)
        }

        Text(
            text = "${perfil.rating}/5.0",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${perfil.totalRatings} calificaciones",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón Calificar Usuario
        Button(
            onClick = { /* TODO: calificar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Calificar Usuario",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Estadísticas en iconos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                icon = "📍",
                value = perfil.puntosVigilados.toString(),
                label = "Puntos Vigilados"
            )

            StatItem(
                icon = "⏱️",
                value = "${perfil.horasVigilancia}h",
                label = "Vigilancia"
            )

            StatItem(
                icon = "🛡️",
                value = "${perfil.confiabilidad}%",
                label = "Confiabilidad"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Título Reseñas
        Text(
            text = "Reseñas Recientes",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Lista de reseñas
        resenas.forEach { resena ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Avatar pequeño de la reseña
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = resena.autor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        // Estrellas de la reseña
                        Text(
                            text = "⭐".repeat(resena.estrellas),
                            fontSize = 14.sp
                        )

                        Text(
                            text = resena.comentario,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = resena.fecha,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón Contacto Seguro
        Button(
            onClick = { /* TODO: contacto seguro */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                "Contacto Seguro",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun StatItem(icon: String, value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}