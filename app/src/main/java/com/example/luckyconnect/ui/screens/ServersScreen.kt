package com.example.luckyconnect.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckyconnect.data.VpnServer
import com.example.luckyconnect.data.VpnConnectionState
import com.example.luckyconnect.ui.components.NeonCard
import com.example.luckyconnect.ui.components.PulsingEffect
import com.example.luckyconnect.ui.theme.FuturisticColors
import com.example.luckyconnect.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServersScreen(
    onBackClick: () -> Unit,
    onServerSelected: (VpnServer) -> Unit = {},
    viewModel: VpnViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        FuturisticColors.DeepSpace,
                        FuturisticColors.DarkMatter
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        // Минимальные фоновые частицы
        val infiniteTransition = rememberInfiniteTransition(label = "background_particles")
        repeat(6) { index ->
            val xOffset by infiniteTransition.animateFloat(
                initialValue = (index * 100).toFloat(),
                targetValue = (index * 100 + 40).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween((12000 + index * 3000), easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particle_x_$index"
            )
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.05f,
                targetValue = 0.12f,
                animationSpec = infiniteRepeatable(
                    animation = tween((5000 + index * 1500), easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particle_alpha_$index"
            )
            
            Box(
                modifier = Modifier
                    .offset(x = xOffset.dp, y = (index * 120).dp)
                    .size(1.dp)
                    .background(
                        color = FuturisticColors.CyberBlue.copy(alpha = alpha),
                        shape = CircleShape
                    )
            )
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Заголовок и кнопка назад
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = FuturisticColors.CardSurface,
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад",
                        tint = FuturisticColors.CyberBlue
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = "Список серверов",
                    color = FuturisticColors.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(80.dp))
            
            // Центральная заглушка - упрощенная версия
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Иконка серверов с пульсацией
                val pulseTransition = rememberInfiniteTransition(label = "server_pulse")
                val pulseScale by pulseTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulse_scale"
                )
                val pulseAlpha by pulseTransition.animateFloat(
                    initialValue = 0.6f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulse_alpha"
                )
                
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(120.dp)
                ) {
                    // Фоновое свечение
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(pulseScale)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        FuturisticColors.CyberBlue.copy(alpha = 0.3f * pulseAlpha),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )
                    )
                    
                    // Основная иконка
                    Icon(
                        imageVector = Icons.Default.Storage,
                        contentDescription = null,
                        tint = FuturisticColors.CyberBlue.copy(alpha = pulseAlpha),
                        modifier = Modifier.size(64.dp)
                    )
                    
                    // Индикатор разработки
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 8.dp, y = (-8).dp)
                            .size(20.dp)
                            .background(
                                color = FuturisticColors.WarningOrange,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                
                // Заголовок
                Text(
                    text = "РАСШИРЕНИЕ СЕТИ",
                    color = FuturisticColors.CyberBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
                
                // Основное сообщение
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Предупреждающий блок о текущем состоянии
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .background(
                                color = FuturisticColors.WarningOrange.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = FuturisticColors.WarningOrange.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = FuturisticColors.WarningOrange,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Text(
                            text = "Сейчас доступен только один оптимальный сервер",
                            color = FuturisticColors.WarningOrange,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
                
                // Прогресс разработки
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = FuturisticColors.WarningOrange,
                            modifier = Modifier.size(20.dp)
                        )
                        
                        Text(
                            text = "В разработке",
                            color = FuturisticColors.WarningOrange,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    // Анимация программирования
                    val codeTransition = rememberInfiniteTransition(label = "coding_animation")
                    
                    // Анимация печатающегося кода
                    val codeProgress by codeTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(3000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "code_progress"
                    )
                    
                    // Мигающий курсор
                    val cursorAlpha by codeTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "cursor_alpha"
                    )
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Область с кодом
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .background(
                                    color = FuturisticColors.VoidBlack.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = FuturisticColors.CyberBlue.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val codeLines = listOf(
                                    "class VpnServer {",
                                    "  fun connect() {",
                                    "    println(\"Connecting...\")",
                                    "  }",
                                    "}"
                                )
                                
                                codeLines.forEachIndexed { index, line ->
                                    val lineProgress = ((codeProgress * codeLines.size) - index).coerceIn(0f, 1f)
                                    val visibleLength = (line.length * lineProgress).toInt()
                                    val visibleText = if (visibleLength > 0) line.take(visibleLength) else ""
                                    
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = visibleText,
                                            color = when {
                                                line.contains("class") || line.contains("fun") -> FuturisticColors.ElectricPurple
                                                line.contains("println") -> FuturisticColors.NeonGreen
                                                line.contains("{") || line.contains("}") -> FuturisticColors.QuantumGold
                                                else -> FuturisticColors.TextPrimary
                                            },
                                            fontSize = 12.sp,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                                        )
                                        
                                        // Показываем курсор только на активной строке
                                        if (visibleLength > 0 && visibleLength < line.length) {
                                            Text(
                                                text = "█",
                                                color = FuturisticColors.CyberBlue.copy(alpha = cursorAlpha),
                                                fontSize = 12.sp,
                                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        
                        Text(
                            text = "Добавление новых локаций...",
                            color = FuturisticColors.TextSecondary,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
} 