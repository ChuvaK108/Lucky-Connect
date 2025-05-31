package com.example.luckyconnect.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckyconnect.data.VpnServer
import com.example.luckyconnect.ui.components.NeonCard
import com.example.luckyconnect.ui.theme.FuturisticColors
import com.example.luckyconnect.viewmodel.VpnViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServersScreen(
    onBackClick: () -> Unit,
    viewModel: VpnViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var showOnlyFree by remember { mutableStateOf(false) }
    val selectedServer by viewModel.selectedServer.collectAsState()
    
    val servers = remember(searchQuery, showOnlyFree) {
        viewModel.getFilteredServers(searchQuery, showOnlyFree)
    }
    
    val serversByRegion = remember(servers) {
        servers.groupBy { server ->
            when {
                server.country in listOf("США", "Канада") -> "Северная Америка"
                server.country in listOf("Великобритания", "Германия", "Нидерланды", "Франция") -> "Европа"
                server.country in listOf("Япония", "Сингапур", "Южная Корея", "Индия") -> "Азия"
                server.country in listOf("Австралия") -> "Океания"
                server.country in listOf("Бразилия") -> "Южная Америка"
                else -> "Другие"
            }
        }
    }
    
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
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
                    text = "Выбор сервера",
                    color = FuturisticColors.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Поиск
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Поиск серверов") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { searchQuery = "" }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Очистить"
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = FuturisticColors.CyberBlue,
                    unfocusedBorderColor = FuturisticColors.TextTertiary,
                    focusedLabelColor = FuturisticColors.CyberBlue,
                    unfocusedLabelColor = FuturisticColors.TextSecondary,
                    focusedTextColor = FuturisticColors.TextPrimary,
                    unfocusedTextColor = FuturisticColors.TextPrimary
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Фильтр бесплатных серверов
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showOnlyFree,
                    onCheckedChange = { showOnlyFree = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = FuturisticColors.CyberBlue,
                        uncheckedColor = FuturisticColors.TextTertiary,
                        checkmarkColor = FuturisticColors.VoidBlack
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Только бесплатные серверы",
                    color = FuturisticColors.TextPrimary,
                    fontSize = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Список серверов по регионам
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                serversByRegion.forEach { (region, regionServers) ->
                    item {
                        Text(
                            text = region,
                            color = FuturisticColors.CyberBlue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                    
                    items(regionServers) { server ->
                        ServerCard(
                            server = server,
                            isSelected = server.id == selectedServer?.id,
                            onClick = {
                                viewModel.connect(server)
                                onBackClick()
                            }
                        )
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
private fun ServerCard(
    server: VpnServer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val accentColor = when {
        isSelected -> FuturisticColors.SuccessGreen
        server.isOptimal -> FuturisticColors.NeonGreen
        server.isPremium -> FuturisticColors.QuantumGold
        else -> FuturisticColors.CyberBlue
    }
    
    val loadColor = when {
        server.load < 30 -> FuturisticColors.SuccessGreen
        server.load < 70 -> FuturisticColors.WarningOrange
        else -> FuturisticColors.ErrorRed
    }
    
    val pingColor = when {
        server.ping < 50 -> FuturisticColors.SuccessGreen
        server.ping < 100 -> FuturisticColors.WarningOrange
        else -> FuturisticColors.ErrorRed
    }
    
    NeonCard(
        accentColor = accentColor,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Флаг страны
                Text(
                    text = server.flagEmoji,
                    fontSize = 32.sp
                )
                
                // Информация о сервере
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = server.name,
                            color = FuturisticColors.TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Значки статуса
                        if (server.isOptimal) {
                            Icon(
                                imageVector = Icons.Default.Bolt,
                                contentDescription = "Оптимальный",
                                tint = FuturisticColors.NeonGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        if (server.isPremium) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Премиум",
                                tint = FuturisticColors.QuantumGold,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Выбран",
                                tint = FuturisticColors.SuccessGreen,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Text(
                        text = "${server.city}, ${server.country}",
                        color = FuturisticColors.TextSecondary,
                        fontSize = 14.sp
                    )
                    
                    // Статистика сервера
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Speed,
                                contentDescription = "Пинг",
                                tint = pingColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "${server.ping}мс",
                                color = pingColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BarChart,
                                contentDescription = "Загрузка",
                                tint = loadColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "${server.load}%",
                                color = loadColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            // Стрелка
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Подключиться",
                tint = accentColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
} 