package com.example.luckyconnect.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckyconnect.data.*
import com.example.luckyconnect.ui.components.*
import com.example.luckyconnect.ui.theme.FuturisticColors
import com.example.luckyconnect.viewmodel.VpnViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigateToServers: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: VpnViewModel = viewModel()
) {
    val connectionState by viewModel.connectionState.collectAsState()
    val selectedServer by viewModel.selectedServer.collectAsState()
    val connectionStats by viewModel.connectionStats.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        FuturisticColors.DeepSpace,
                        FuturisticColors.DarkMatter,
                        FuturisticColors.MidnightBlue.copy(alpha = 0.3f)
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            // Заголовок приложения
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LUCKY CONNECT",
                        color = FuturisticColors.CyberBlue,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Защищённое подключение",
                        color = FuturisticColors.TextSecondary,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            // Главная кнопка подключения
            item {
                FuturisticConnectionButton(
                    connectionState = connectionState,
                    onClick = { viewModel.toggleConnection() },
                    modifier = Modifier.padding(vertical = 32.dp)
                )
            }
            
            // Информация о выбранном сервере
            item {
                selectedServer?.let { server ->
                    NeonCard(
                        accentColor = when (connectionState) {
                            VpnConnectionState.CONNECTED -> FuturisticColors.SuccessGreen
                            VpnConnectionState.CONNECTING, VpnConnectionState.DISCONNECTING -> FuturisticColors.WarningOrange
                            VpnConnectionState.ERROR -> FuturisticColors.ErrorRed
                            else -> FuturisticColors.CyberBlue
                        },
                        onClick = onNavigateToServers
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = server.flagEmoji,
                                    fontSize = 32.sp
                                )
                                Column {
                                    Text(
                                        text = server.name,
                                        color = FuturisticColors.TextPrimary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${server.city}, ${server.country}",
                                        color = FuturisticColors.TextSecondary,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Изменить сервер",
                                tint = FuturisticColors.TextSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                } ?: run {
                    NeonCard(
                        accentColor = FuturisticColors.CyberBlue,
                        onClick = onNavigateToServers
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Выберите сервер",
                                    color = FuturisticColors.TextPrimary,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Нажмите для выбора",
                                    color = FuturisticColors.TextSecondary,
                                    fontSize = 14.sp
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Выбрать сервер",
                                tint = FuturisticColors.TextSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            
            // Быстрые действия
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NeonCard(
                        modifier = Modifier.weight(1f),
                        accentColor = FuturisticColors.ElectricPurple,
                        onClick = onNavigateToServers
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Серверы",
                                tint = FuturisticColors.ElectricPurple,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Серверы",
                                color = FuturisticColors.TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    
                    NeonCard(
                        modifier = Modifier.weight(1f),
                        accentColor = FuturisticColors.QuantumGold,
                        onClick = onNavigateToSettings
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Настройки",
                                tint = FuturisticColors.QuantumGold,
                                modifier = Modifier.size(32.dp)
                            )
                            Text(
                                text = "Настройки",
                                color = FuturisticColors.TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
} 